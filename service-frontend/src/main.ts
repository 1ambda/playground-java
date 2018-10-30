import Vue from "vue"
import Router from "vue-router"

import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import locale from "element-ui/lib/locale/lang/en"
import "vue-awesome/icons"

import App from "@/App.vue"
import routes from "@/router.ts"
import store from "@/store"
import * as Mutations from "@/store/mutation_type"
import * as RouteType from "@/store/route_type"

import "@/registerServiceWorker"

import {AuthAPI} from "@/common/auth.service.ts"
import {Failure} from "@/generated/swagger"

import Icon from "vue-awesome/components/Icon.vue"
import BackToTop from "vue-backtotop"

/**
 * error handlers
 */
Vue.config.errorHandler = (err, vm, info) => {
  console.log(`Captured in Vue.config.errorHandler : ${info}`, err,)
}
window.addEventListener("error", event => {
  console.log("Captured in error EventListener", event.error)
})

window.addEventListener("unhandledrejection", event => {
  console.log("Captured in unhandledrejection EventListener", event.reason)
})

Vue.config.productionTip = false
Vue.use(ElementUI, {locale})
Vue.component("v-icon", Icon)
Vue.component("back-to-top", BackToTop)

const router = new Router({
  routes,
})

AuthAPI.whoiam({credentials: "include"})
  .then((response) => {
    router.beforeEach((to: any, from: any, next: any) => {
      // if the page doesn't require authentication, move to the page
      if (!to.matched.some((record: any) => record.meta.requiresAuth)) {
        return next()
      }

      // check user is authenticated
      const username = store.state.username
      if (!username) {
        if (to.name === RouteType.LOGIN) {
          return next(RouteType.LOGIN)
        }

        // if not, redirect to the login page with flash message
        store.commit(Mutations.AUTH__SET_FLASH_MESSAGE, `Please Login for '${to.path}'`)
        return next(RouteType.LOGIN)
      }

      next()
    })

    if (!response.username || response.username.trim() === "") {
      store.commit(Mutations.AUTH__LOGOUT)
      router.push(`/${RouteType.LOGIN}`)
      return
    }

    store.commit(Mutations.AUTH__LOGIN, response.username)
  })
  .catch((response) => {
    if (!response.json) {
      console.error("Unknown error occurred", response)

      return
    }
    response.json().then((parsed: Failure) => {
      console.error(parsed)
      router.push(`/${RouteType.LOGIN}`)
    })
  })

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app")
