import Vue from "vue"

import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import locale from "element-ui/lib/locale/lang/en"
import "vue-awesome/icons"

import App from "@/App.vue"
import {Router,} from "@/router.ts"
import store from "@/store"
import * as Mutations from "@/store/mutation_type"
import * as RouteType from "@/store/route_type"

import "@/registerServiceWorker"

import {AuthAPI} from "@/common/auth.service.ts"

import Icon from "vue-awesome/components/Icon.vue"
import BackToTop from "vue-backtotop"
import {handleFailure} from "@/common/failure.util"

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

AuthAPI.whoiam({credentials: "include"})
  .then((response) => {
    Router.beforeEach((to: any, from: any, next: any) => {
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
      Router.push(`/${RouteType.LOGIN}`)
      return
    }

    store.commit(Mutations.AUTH__LOGIN, response.username)
  })
  .catch(handleFailure)

new Vue({
  router: Router,
  store: store,
  render: (h) => h(App),
}).$mount("#app")
