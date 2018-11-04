import {Failure} from "@/generated/swagger"

import {Notification} from "element-ui"
import {Router,} from "@/router.ts"

function isPromise(response: any) {
  return response.promise
}

function isUnhandledPromise(response: any) {
  return isPromise(response) && response.type === "unhandledrejection"
}

const NotificationTextStyle = "font-size: 15px; text-align: start;"

const defaultOffset = 20

const displayError = ({title, message, offset}) => {
  Notification.error({
    offset: offset,
    title: title,
    message: message,
    type: "error",
    customClass: "global-title-alert-error",
  })
}

const displayWarning = ({title, message, offset}) => {
  Notification.error({
    offset: offset,
    title: title,
    message: message,
    type: "warning",
    customClass: "global-title-alert-warning",
  })
}

const displayInfo = ({title, message, offset}) => {
  Notification.error({
    offset: offset,
    title: title,
    message: message,
    type: "info",
    customClass: "global-title-alert-info",
  })
}


const displaySuccess = ({title, message, offset}) => {
  Notification.error({
    offset: offset,
    title: title,
    message: message,
    type: "success",
    customClass: "global-title-alert-success",
  })
}


export const handleFailure = (response: any) => {
  if (isUnhandledPromise(response)) {
    response = response.reason
  }

  if (response.type === "cors" && response.status === 401) {

    if (response.url && response.url.includes("/api/auth/login")) {
      displayError({
        offset: defaultOffset,
        title: `Error (Unauthorized)`,
        message: "Username or password is invalid. Please check it.",
      })

      return
    }

    Router.push({path: "login"})

    displayError({
      offset: defaultOffset,
      title: `Error (Unauthorized)`,
      message: "Not authorized, Please login.",
    })
    return
  }

  // client runtime error in promise
  if (response.stack && response.message) {
    console.error(response)

    if (response.message.includes("Failed to fetch")) {
      displayError({
        offset: defaultOffset,
        title: `Error (Unhandled Rejection)`,
        message: response.message,
      })
      return
    }

    displayError({
      offset: defaultOffset,
      title: `Error (Client Runtime)`,
      message: response.message,
    })
    return
  }

  // server returned non-json response
  if (!response.json) {
    console.error(response)

    displayError({
      offset: defaultOffset,
      title: `Error (Server Connection)`,
      message: "Server is not available",
    })

    return
  }

  // handle NotFound
  if (response.status === 404) {
    displayError({
      offset: defaultOffset,
      title: `Error (Not Found)`,
      message: `${response.url}`,
    })
    return
  }

  // formatted error returned from server
  response.json().then((parsed: Failure) => {
    const splitted = parsed.type.split(".")
    const canonical = splitted[splitted.length - 1]

    // session timed-out
    if (canonical === "InsufficientAuthenticationException") {
      displayWarning({
        offset: defaultOffset,
        title: `Session Expired`,
        message: "Please re-login.",
      })

      Router.push({path: "login"})
      return
    }

    displayError({
      offset: defaultOffset,
      title: `Error (${canonical})`,
      message: parsed.message,
    })
  })
}