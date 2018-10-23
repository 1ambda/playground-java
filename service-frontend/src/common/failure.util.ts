import {Failure} from "@/generated/swagger";

export const handleFailure = (notifier: any) => {
  return (response: any) => {
    if (!response.json) {
      notifier.error({
        title: `Error (Connection)`,
        message: "Server is not available",
      })

      return
    }

    if (response.status === 404) {
      notifier.error({
        title: `Error (Not Found)`,
        message: `${response.url}`,
      })
      return
    }

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