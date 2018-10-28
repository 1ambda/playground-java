import Vue from "vue"
import Vuex from "vuex"

import mutations from "@/store/mutation.ts"
import actions from "@/store/action.ts"
import * as States from "@/store/state_type"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: "", // indicates user is logged in
    flashMessage: "",
    path: "",

    [States.PRODUCT__FETCHED_ITEMS]: [],
    [States.PRODUCT__TOTAL_COUNT]: 0,
    [States.PRODUCT__CURRENT_PAGE]: 1,

  },
  getters: {
    authenticated: (state: any) => {
      return state.username !== ""
    },
    username: (state: any) => {
      return state.username
    },
  },
  mutations,
  actions,
})

