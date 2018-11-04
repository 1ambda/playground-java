import * as States from "@/store/state_type"

export const getDefaultState = () => {
  return {
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
    [States.PRODUCT__SEARCH_AVAILABLE_CATEGORIES]: [
      {value: "all", label: "All"},
    ],

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
  }
}
