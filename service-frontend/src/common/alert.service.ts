import {Notification} from "element-ui"

const NotificationTextStyle = "font-size: 15px; text-align: start;"
const defaultOffset = 70

export const displayError = (params) => {
  const {offset, title, message} = params

  Notification.error({
    offset: offset ? offset : defaultOffset,
    title: title,
    message: message,
    type: "error",
    customClass: "global-title-alert-error",
  })
}

export const displayWarning = (params) => {
  const {offset, title, message} = params

  Notification.error({
    offset: offset ? offset : defaultOffset,
    title: title,
    message: message,
    type: "warning",
    customClass: "global-title-alert-warning",
  })
}

export const displayInfo = (params) => {
  const {offset, title, message} = params

  Notification.error({
    offset: offset ? offset : defaultOffset,
    title: title,
    message: message,
    type: "info",
    customClass: "global-title-alert-info",
  })
}


export const displaySuccess = (params) => {
  const {offset, title, message} = params

  Notification.error({
    offset: offset ? offset : defaultOffset,
    title: title,
    message: message,
    type: "success",
    customClass: "global-title-alert-success",
  })
}

