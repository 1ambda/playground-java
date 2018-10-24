import {Failure} from "@/generated/swagger";

export const handleFailure = (notifier: any) => {
  return (response: any) => {

    // client runtime error in promise
    if (response.stack && response.message) {
      console.error(response)

      if (response.message.includes('Failed to fetch')) {
        notifier.error({
          title: `Error (Network)`,
          message: response.message,
        })
        return
      }

      notifier.error({
        title: `Error (Client)`,
        message: response.message,
      })
      return
    }

    // server returned non-json response
    if (!response.json) {
      notifier.error({
        title: `Error (Connection)`,
        message: "Server is not available",
      })

      return
    }

    // handle NotFound
    if (response.status === 404) {
      notifier.error({
        title: `Error (Not Found)`,
        message: `${response.url}`,
      })
      return
    }

    // formatted error returned from server
    response.json().then((parsed: Failure) => {
      const splitted = parsed.type.split('.')
      const canonical = splitted[splitted.length - 1]

      notifier.error({
        title: `Error (${canonical})`,
        message: parsed.message,
      })
    })
  }
}