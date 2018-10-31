import Vue from "vue"
import Vuex from "vuex"

import mutations from "@/store/mutation.ts"
import actions from "@/store/action.ts"
import * as States from "@/store/state_type"
import * as Getters from "@/store/getter_type"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

    [States.AUTH__USERNAME]: "", // indicates user is logged in if not empty
    [States.AUTH__FLASH_MESSAGE]: "",

    [States.PRODUCT__FETCHED_ITEMS]: [],
    [States.PRODUCT__TOTAL_COUNT]: 0,
    [States.PRODUCT__CURRENT_PAGE]: 1,
    [States.PRODUCT__FILTER_MIN_PRICE]: 0,
    [States.PRODUCT__FILTER_MAX_PRICE]: null,
    [States.PRODUCT__ITEM_COUNT]: 8,

  },
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

