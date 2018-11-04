import * as Mutations from "@/store/mutation_type"
import * as States from "@/store/state_type"
import {CartLineDTO, CategoryDTO, ProductDTO, ProductOptionDTO} from "@/generated/swagger"
import {getDefaultState} from "@/store/state_default"

export default {
  /**
   * AUTH
   */
  [Mutations.AUTH__CLEAR_FLASH_MESSAGE](state: any) {
    state[States.AUTH__FLASH_MESSAGE] = ""
  },

  [Mutations.AUTH__SET_FLASH_MESSAGE](state: any, message: string) {
    state[States.AUTH__FLASH_MESSAGE] = message
  },

  [Mutations.AUTH__LOGOUT](state: any) {
    const initialState = getDefaultState()
    state = Object.assign(state, initialState)
  },

  [Mutations.AUTH__LOGIN](state: any, username: string) {
    state[States.AUTH__USERNAME] = username
  },

  /**
   * PRODUCT
   */
  [Mutations.PRODUCT__UPDATE_ITEMS](state: any, products) {
    state[States.PRODUCT__FETCHED_ITEMS] = products
  },

  [Mutations.PRODUCT__UPDATE_TOTAL_ITEM_COUNT](state: any, totalCount) {
    state[States.PRODUCT__TOTAL_COUNT] = totalCount
  },

  [Mutations.PRODUCT__UPDATE_CURRENT_PAGE](state: any, currentPage) {
    state[States.PRODUCT__CURRENT_PAGE] = currentPage
  },

  [Mutations.PRODUCT__UPDATE_ITEM_COUNT_PER_PAGE](state: any, itemCountPerPage) {
    state[States.PRODUCT__CURRENT_SIZE] = itemCountPerPage
  },

  /**
   * PRODUCT.FILTER
   */
  [Mutations.PRODUCT__UPDATE_FILTER_PRICE](state: any, {minPrice, maxPrice}) {
    state[States.PRODUCT__FILTER_MIN_PRICE] = minPrice
    state[States.PRODUCT__FILTER_MAX_PRICE] = maxPrice
  },

  [Mutations.PRODUCT__RESET_FILTER_PRICE](state: any) {
    state[States.PRODUCT__FILTER_MIN_PRICE] = 0
    state[States.PRODUCT__FILTER_MAX_PRICE] = null
  },

  /**
   * PRODUCT.SEARCH
   */
  [Mutations.PRODUCT__SEARCH_UPDATE_KEYWORD](state: any, keyword: string) {
    state[States.PRODUCT__SEARCH_KEYWORD] = keyword
  },

  [Mutations.PRODUCT__SEARCH_UPDATE_CATEGORY](state: any, categoryId: string) {
    state[States.PRODUCT__SEARCH_CATEGORY] = categoryId
  },

  [Mutations.PRODUCT__SEARCH_UPDATE_CATEGORY_LIST](state: any, categories: Array<CategoryDTO>) {
    const converted = categories.map(category => {
      const value = `${category.id}`
      const label = category.displayName
      return {value: value, label: label,}
    })

    const withDefaultCategory = [{
      value: "all", label: "All"
    }, ...converted]

    state[States.PRODUCT__SEARCH_AVAILABLE_CATEGORIES] = withDefaultCategory
  },

  /**
   * DETAIL
   */
  [Mutations.DETAIL__SET_PRODUCT_ITEM](state: any, productItem: ProductDTO) {
    state[States.DETAIL__PRODUCT_ITEM] = productItem
  },

  [Mutations.DETAIL__SET_PRODUCT_OPTION_LIST](state: any, productOptionList: Array<ProductOptionDTO>) {
    state[States.DETAIL__PRODUCT_OPTION_LIST] = productOptionList
  },

  [Mutations.DETAIL__SET_QUANTITY](state: any, quantity: number) {
    state[States.DETAIL__PRODUCT_QUANTITY] = quantity
  },

  /**
   * CART
   */
  [Mutations.CART__SET_CART_LINE_LIST](state: any, cartLineList: Array<CartLineDTO>) {
    state[States.CART__LINE_LIST] = cartLineList
  },
}
