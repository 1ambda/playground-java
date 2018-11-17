import SockJS from "sockjs-client"
import {BehaviorSubject, Observable, ReplaySubject} from "rxjs"
import * as Endpoint from "@/common/endpoint.service.ts"
import {Failure, WebsocketMessageBase, WebsocketMessageType} from "@/generated/swagger"

const WebsocketStatus = {
  // if current sock is null
  INIT: "INIT",

  // statues belows are SockJS status.
  OPEN: "OPEN",
  CLOSED: "CLOSED",
}

const endpoint = Endpoint.WS_AUTHENTICATED

const reconnectDelay = 2000 // millis
const replayMaxBuffer = 500 // items, not to blow up browser memory :)
let currentSock = null
let reconnectInterval = null

const receiveQueue: ReplaySubject<any> = new ReplaySubject(replayMaxBuffer)
const websocketStatus: BehaviorSubject<string> = new BehaviorSubject(WebsocketStatus.INIT)

// TODO: https://github.com/ohjames/rxjs-websockets/blob/master/src/index.ts
export const connect = () => {
  if (isOpen()) {
    console.warn("[WEBSOCKET]: already connected.")
    return
  }

  const sock = new SockJS(`${endpoint}`)
  clearInterval(reconnectInterval)

  sock.onopen = function () {
    console.warn("[WEBSOCKET]: `onopen`")

    websocketStatus.next(WebsocketStatus.OPEN)
  }

  sock.onclose = function () {
    console.warn("[WEBSOCKET]: `onclose`")

    websocketStatus.next(WebsocketStatus.CLOSED)

    if (getWebsocketStatus() === WebsocketStatus.INIT) {
      return
    }

    // websocket is closed unintentionally, try reconnect
    console.warn("[WEBSOCKET]: Closed unintentionally. Reconnecting...")
    reconnectInterval = setInterval(() => {
      connect()
    }, reconnectDelay)
  }

  sock.onerror = function (error: any) {
    let message = error.message
    if (error.message === "" || error.message === undefined || error.message === null) {
      message = "Connection refused"
    }

    console.error("[WEBSOCKET]: `onerror`", message)
  }

  sock.onmessage = function (response: any) {
    if (!response || !response.data) {
      // TODO
      console.error(`[WEBSOCKET]: Unknown response:`, response)
      return
    }

    let formatted = null

    try {
      formatted = JSON.parse(response.data)
    } catch (e) {
      console.error("Failed to parse Websocket's response.data", e)
      return
    }

    if (!formatted.header || !formatted.header.type) {
      // TODO
      console.error(`[WEBSOCKET]: Unknown response.data:`, formatted)
      return
    }

    if (formatted.header.type === WebsocketMessageType.Error) {
      const failure: Failure = formatted.header.failure
      console.error(`[WEBSOCKET]: Error:`, failure)
    } else {
      console.debug(`[WEBSOCKET]: Received websocket message:`, formatted)
    }

    receiveQueue.next(formatted)
  }

  currentSock = sock

  return sock
}

export const disconnect = () => {
  if (!currentSock || isOpen()) {
    console.error(`[WEBSOCKET]: Can't disconnect due to socket`, currentSock)
    return
  }

  currentSock.close()
  currentSock = null
}

/**
 * Since we can't get events for `CLOSING` and `CONNECTING,
 * wrap those statues into `CLOSED` and `OPEN` in the getWebsocketStatus function
 */
function getWebsocketStatus() {
  if (!currentSock) {
    return WebsocketStatus.INIT
  }

  const status = currentSock.readyState

  if (status === 0) { /** CONNECTING */
    return WebsocketStatus.OPEN
  }

  if (status === 1) { /** OPEN */
    return WebsocketStatus.OPEN
  }

  if (status === 2) { /** CLOSING */
    return WebsocketStatus.CLOSED
  }

  return WebsocketStatus.CLOSED
}

function isOpen() {
  return getWebsocketStatus() === WebsocketStatus.OPEN
}

export const send = (data: WebsocketMessageBase) => {
  if (!isOpen()) {
    console.warn("[WEBSOCKET]: Not connected. Message will not be sent")
    return false
  }

  console.debug(`[WEBSOCKET]: Sending websocket message`, data)

  currentSock.send(JSON.stringify(data))

  return true
}

function watch(targetResponseType: WebsocketMessageType): Observable<any> {
  return receiveQueue.filter((response: any) => {
    const eventType = response.header.type

    return (eventType === targetResponseType)
  })
}

export const Watchers = {

  Error: (): Observable<WebsocketMessageBase> => {
    return watch(WebsocketMessageType.Error)
  },

  Notification: (): Observable<WebsocketMessageBase> => {
    return watch(WebsocketMessageType.Notification)
  },
}


