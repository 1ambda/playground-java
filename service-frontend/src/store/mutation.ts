import * as Mutations from "@/store/mutation_type"
import * as States from "@/store/state_type"

export default {
  cleanFlashMessage(state: any) {
    state.flashMessage = ""
  },

  setFlashMessage(state: any, message: string) {
    state.flashMessage = message
  },

  logout(state: any) {
    state.username = ""
  },

  login(state: any, username: string) {
    state.username = username
  },

  changePath(state: any, path: string) {
    state.path = path
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
