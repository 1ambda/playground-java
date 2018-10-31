import * as Actions from "@/store/action_type"

export default {
  [Actions.PRODUCT__FETCH_PAGINATED_ITEMS]: async ({state, getters, commit, dispatch}) => {
    const currentPage = this.stateCurrentPage
    const page = currentPage - 1
    const size = this.stateItemCountPerPage
    const sort = []
    const options = {credentials: "include"}

    const categoryId = null
    const search = ""

    const minPrice = this.filterMinPrice
    const maxPrice = this.filterMaxPrice
    const minRate = null
    const tags = []
    const minShippingDate = null

    // CatalogAPI.findPaginatedProducts(
    //   page, size, sort,
    //   categoryId, search,
    //   minPrice, maxPrice, minRate, tags, minShippingDate, options)
    //   .then((response: any) => {
    //     const pagination: Pagination = response.pagination
    //
    //     const totalItemCount = pagination.totalItemCount
    //     this.commitUpdateTotalCount(totalItemCount)
    //
    //     const products = response.products.map(p => p.item)
    //     this.commitUpdateItems(products)
    //
    //     // TODO import { Notification } from 'element-ui';
    //   }).catch(handleFailure)
  },
}
