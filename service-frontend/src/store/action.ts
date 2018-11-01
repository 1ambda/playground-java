import * as Actions from "@/store/action_type"
import * as States from "@/store/state_type"
import * as Mutations from "@/store/mutation_type"
import {CatalogAPI} from "@/common/product.service"
import {Pagination} from "@/generated/swagger"
import {handleFailure} from "@/common/failure.util"

export default {
  [Actions.PRODUCT__FETCH_PAGINATED_ITEMS]: async ({state, getters, commit, dispatch}) => {
    const options = {credentials: "include"}

    const page = state[States.PRODUCT__CURRENT_PAGE]
    const size = state[States.PRODUCT__ITEM_COUNT_PER_PAGE]

    const sort = []
    const categoryId = null
    const search = ""

    const minPrice = state[States.PRODUCT__FILTER_MIN_PRICE]
    const maxPrice = state[States.PRODUCT__FILTER_MAX_PRICE]

    // not implemented
    const minRate = null
    const tags = []
    const minShippingDate = null

    const {pagination, products} = await CatalogAPI.findPaginatedProducts(
      page, size, sort,
      categoryId, search,
      minPrice, maxPrice, minRate, tags, minShippingDate, options)
      .then((response: any) => {
        const pagination: Pagination = response.pagination
        const products = response.products.map(p => p.item)

        return {pagination, products,}
      })

    const totalItemCount = pagination.totalItemCount
    const itemCountPerPage = pagination.itemCountPerPage

    commit(Mutations.PRODUCT__UPDATE_TOTAL_ITEM_COUNT, totalItemCount)
    commit(Mutations.PRODUCT__UPDATE_ITEMS, products)
  },
}
