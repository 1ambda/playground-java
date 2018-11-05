import Vue from "vue"
import {sync} from "vuex-router-sync"

import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import 'element-ui/lib/theme-chalk/display.css'
import locale from "element-ui/lib/locale/lang/en"
import "vue-awesome/icons"

import App from "@/App.vue"
import {Router, Routes,} from "@/router.ts"
import store from "@/store"
import * as States from "@/store/state_type"
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
Vue.config.errorHandler = (error, vm, info) => {
  console.error(`Vue.config.errorHandler: ${info}`)
  handleFailure(error)
}
window.addEventListener("error", error => {
  handleFailure(error)
})

window.addEventListener("unhandledrejection", response => {
  handleFailure(response)
})

Vue.config.productionTip = false
Vue.use(ElementUI, {locale})
Vue.component("v-icon", Icon)
Vue.component("back-to-top", BackToTop)

sync(store, Router)

AuthAPI.whoiam({credentials: "include"})
  .then((response) => {
    // set router hook after fetching whoiam response
    Router.beforeEach((to: any, from: any, next: any) => {

      // if the page doesn't require authentication, move to the page
      if (!to.matched.some((record: any) => record.meta.requiresAuth)) {
        next()
        return
      }

      // check user is authenticated
      const username = store.state[States.AUTH__USERNAME]
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
      return
    }

    store.commit(Mutations.AUTH__LOGIN, response.username)

    const currentPath = store.state.route.path
    const index = Routes.findIndex(r => currentPath === r.path)

    // user already logged-in but if page doesn't require auth, then move to `/`
    if (index === -1 || index >= Routes.length) {
      console.error(`current path is ${currentPath} but it doesn't exist in available routes`)
      Router.push(`/${RouteType.HOME}`)
      return
    }

    const route = Routes[index]
    Router.push(route.path)
  })

new Vue({
  router: Router,
  store: store,
  render: (h) => h(App),
}).$mount("#app")
