import {Failure} from "@/generated/swagger"

import {Router,} from "@/router.ts"
import * as Alerts from "@/common/alert.service.ts"

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

    if (response.url && response.url.includes("/api/auth/login")) {
      Alerts.displayError({
        title: `Error (Unauthorized)`,
        message: "Username or password is invalid. Please check it.",
      })

      return
    }

    Router.push({path: "login"})

    Alerts.displayError({
      title: `Error (Unauthorized)`,
      message: "Not authorized, Please login.",
    })
    return
  }

  // client runtime error in promise
  if (response.stack && response.message) {
    console.error(response)

    if (response.message.includes("Failed to fetch")) {
      Alerts.displayError({
        title: `Error (Server Connection)`,
        message: "Server is not available",
      })
      return
    }

    Alerts.displayError({
      title: `Error (Client Runtime)`,
      message: response.message,
    })
    return
  }

  // server returned non-json response
  if (!response.json) {
    console.error(response)

    Alerts.displayError({
      title: `Error (Unknown Response)`,
      message: response,
    })

    return
  }

  // handle NotFound
  if (response.status === 404) {
    Alerts.displayError({
      title: `Error (Not Found)`,
      message: `${response.url}`,
    })
    return
  }

  // formatted error returned from server
  response.json().then((parsed: Failure) => {
    const splitted = parsed.type.split(".")
    const canonical = splitted[splitted.length - 1]
    const errorType = canonical.replace(/Exception/g, "")

    // session timed-out
    if (canonical === "InsufficientAuthenticationException") {
      Alerts.displayWarning({
        title: `Session Expired`,
        message: "Please re-login.",
      })

      Router.push({path: "login"})
      return
    }

    Alerts.displayError({
      title: `Error (${errorType})`,
      message: parsed.message,
    })
  })
}