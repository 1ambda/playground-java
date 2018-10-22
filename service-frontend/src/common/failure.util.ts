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

    response.json().then((parsed: Failure) => {
      notifier.error({
        title: `Error (${parsed.type})`,
        message: parsed.message,
      })
    })
  }
}