import * as Mutations from "@/store/mutation_type"
import * as States from "@/store/state_type"

export default {
  [Mutations.AUTH__CLEAR_FLASH_MESSAGE](state: any) {
    state[States.AUTH__FLASH_MESSAGE] = ""
  },

  [Mutations.AUTH__SET_FLASH_MESSAGE](state: any, message: string) {
    state[States.AUTH__FLASH_MESSAGE] = message
  },

  [Mutations.AUTH__LOGOUT](state: any) {
    state[States.AUTH__USERNAME] = ""
  },

  [Mutations.AUTH__LOGIN](state: any, username: string) {
    state[States.AUTH__USERNAME] = username
  },

  [Mutations.PRODUCT__UPDATE_ITEMS](state: any, products) {
    state[States.PRODUCT__FETCHED_ITEMS] = products
  },

  [Mutations.PRODUCT__UPDATE_TOTAL_COUNT](state: any, totalCount) {
    state[States.PRODUCT__TOTAL_COUNT] = totalCount
  },

  [Mutations.PRODUCT__UPDATE_CURRENT_PAGE](state: any, currentPage) {
    state[States.PRODUCT__CURRENT_PAGE] = currentPage
  },

  [Mutations.PRODUCT__UPDATE_FILTER_PRICE](state: any, {minPrice, maxPrice}) {
    state[States.PRODUCT__FILTER_MIN_PRICE] = minPrice
    state[States.PRODUCT__FILTER_MAX_PRICE] = maxPrice
  },

  [Mutations.PRODUCT__RESET_FILTER_PRICE](state: any) {
    state[States.PRODUCT__FILTER_MIN_PRICE] = 0
    state[States.PRODUCT__FILTER_MAX_PRICE] = null
  },
}
