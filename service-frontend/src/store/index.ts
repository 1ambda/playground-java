import Vue from "vue"
import Vuex from "vuex"

import mutations from "@/store/mutation.ts"
import actions from "@/store/action.ts"
import * as States from "@/store/state_type"
import * as Getters from "@/store/getter_type"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    /**
     * AUTH
     */
    [States.AUTH__USERNAME]: "", // indicates user is logged in if not empty
    [States.AUTH__FLASH_MESSAGE]: "",

    /**
     * PRODUCT
     */
    [States.PRODUCT__FETCHED_ITEMS]: [],
    [States.PRODUCT__TOTAL_COUNT]: 0,
    [States.PRODUCT__CURRENT_PAGE]: 1,
    [States.PRODUCT__FILTER_MIN_PRICE]: 0,
    [States.PRODUCT__FILTER_MAX_PRICE]: null,
    [States.PRODUCT__CURRENT_SIZE]: 8,

    [States.PRODUCT__SEARCH_KEYWORD]: "",
    [States.PRODUCT__SEARCH_CATEGORY]: "all",
    [States.PRODUCT__SEARCH_AVAILABLE_CATEGORIES]: [{value: "all", label: "All"},],

    /**
     * DETAIL
     */
    [States.DETAIL__PRODUCT_ID]: null,
    [States.DETAIL__PRODUCT_OPTION_LIST]: [],
    [States.DETAIL__PRODUCT_ITEM]: {},
    [States.DETAIL__PRODUCT_QUANTITY]: 1,
    [States.DETAIL__REVIEW_RATE]: 5,

    /**
     * CART
     */
    [States.CART__LINE_LIST]: [],

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

