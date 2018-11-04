import {Failure} from "@/generated/swagger"

import {Notification} from "element-ui"
import {Router,} from "@/router.ts"

function isPromise(response: any) {
  return response.promise
}

function isUnhandledPromise(response: any) {
  return isPromise(response) && response.type === "unhandledrejection"
}

export const handleFailure = (response: any) => {
  if (isUnhandledPromise(response)) {
    response = response.reason
  }

  if (response.type === "cors" && response.status === 401) {
    Router.push({path: "login"})

    Notification.error({
      title: `Error (Unauthorized)`,
      message: "Not authorized, Please login.",
    })
    return
  }

  // client runtime error in promise
  if (response.stack && response.message) {
    console.error(response)

    if (response.message.includes("Failed to fetch")) {
      Notification.error({
        title: `Error (Unhandled Rejection)`,
        message: response.message,
      })
      return
    }

    Notification.error({
      title: `Error (Client)`,
      message: response.message,
    })
    return
  }

  // server returned non-json response
  if (!response.json) {
    console.error(response)

    Notification.error({
      title: `Error (Connection)`,
      message: "Server is not available",
    })

    return
  }

  // handle NotFound
  if (response.status === 404) {
    Notification.error({
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
      Notification.warning({
        title: `Session Expired`,
        message: "Please re-login.",
      })

      Router.push({path: "login"})
      return
    }

    Notification.error({
      title: `Error (${canonical})`,
      message: parsed.message,
    })
  })
}