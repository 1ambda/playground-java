import * as Actions from "@/store/action_type"
import * as States from "@/store/state_type"
import * as Mutations from "@/store/mutation_type"
import {CatalogAPI} from "@/common/product.service"
import {CategoryListDTO, Pagination} from "@/generated/swagger"

const Product_FetchPaginatedItems = async ({state, getters, commit, dispatch}) => {
  const options = {credentials: "include"}

  const page = state[States.PRODUCT__CURRENT_PAGE]
  const size = state[States.PRODUCT__CURRENT_SIZE]
  const sort = []

  let categoryId = state[States.PRODUCT__SEARCH_CATEGORY]
  if (categoryId || !Number(categoryId) || categoryId.trim().length < 1 || categoryId === "all") {
    categoryId = Number(categoryId)
  } else {
    categoryId = null
  }

  let search = state[States.PRODUCT__SEARCH_KEYWORD]
  if (!search || search.trim().length < 1) {
    search = null
  }

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
}

const Product_FetchCategoryList = async ({state, getters, commit, dispatch}) => {
  const options = {credentials: "include"}
  const dto: CategoryListDTO = await CatalogAPI.findAllCategories(options)

  commit(Mutations.PRODUCT__SEARCH_UPDATE_CATEGORY_LIST, dto.items)
}

export default {
  [Actions.PRODUCT__FETCH_PAGINATED_ITEMS]: Product_FetchPaginatedItems,
  [Actions.PRODUCT__FETCH_CATEGORY_LIST]: Product_FetchCategoryList,
}
