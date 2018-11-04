import Vue from "vue"
import Vuex from "vuex"

import mutations from "@/store/mutation"
import actions from "@/store/action"
import * as States from "@/store/state_type"
import * as Getters from "@/store/getter_type"
import {getDefaultState} from "@/store/state_default"

Vue.use(Vuex)

const initialState = getDefaultState()

export default new Vuex.Store({
  state: initialState,

  getters: {
    [Getters.AUTH__AUTHENTICATED]: (state: any) => {
      return state[States.AUTH__USERNAME] !== "" &&
        typeof state[States.AUTH__USERNAME] !== "undefined" &&
        state[States.AUTH__USERNAME] !== null
    },

  },
  mutations,
  actions,
})

