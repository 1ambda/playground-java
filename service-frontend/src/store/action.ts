import * as Actions from "@/store/action_type"
import * as States from "@/store/state_type"
import * as Mutations from "@/store/mutation_type"
import {CartAPI, CatalogAPI} from "@/common/product.service"
import {
  CartDTO,
  CartLineDTO,
  CartLineOptionDTO,
  CategoryListDTO,
  Pagination,
  ProductContainerDTO,
  ProductDTO,
  ProductOptionDTO, UserDTO
} from "@/generated/swagger"
import {AuthAPI} from "@/common/auth.service"
import {REGISTER} from "@/store/route_type"

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

const Detail_FetchProductItem = async (
  {state, getters, commit, dispatch},
  productId) => {

  const response: ProductContainerDTO = await CatalogAPI.findOneProduct(productId, {credentials: "include"})

  const productItem: ProductDTO = response.item
  const productOptions: Array<ProductOptionDTO> = response.options

  commit(Mutations.DETAIL__SET_PRODUCT_ITEM, productItem)
  commit(Mutations.DETAIL__SET_PRODUCT_OPTION_LIST, productOptions)
}

const Detail_AddToCart = async (
  {state, getters, commit, dispatch},
  {productId, productOptionIdList}) => {

  const quantity = state[States.DETAIL__PRODUCT_QUANTITY]
  const optionQuantity = 1 // support only 1 quantity for option

  const cartLineOptionRequestDTOs = productOptionIdList.map(productOptionId => {
    const optionRequestDTO: CartLineOptionDTO = {
      productOptionId: productOptionId,
      quantity: optionQuantity,
    }

    return optionRequestDTO
  })

  const request: CartLineDTO = {
    quantity: quantity,
    productId: productId,
    cartLineOptions: cartLineOptionRequestDTOs,
  }

  await CartAPI.addUserCartLine(request, {credentials: "include"})
}

const Cart_FetchCartLineList = async (
  {state, getters, commit, dispatch}) => {

  const fetched: CartDTO = await CartAPI.getUserCartLines({credentials: "include"})

  commit(Mutations.CART__SET_CART_LINE_LIST, fetched.cartLines)
}

const Cart_UpdateCartLine = async (
  {state, getters, commit, dispatch},
  {cartLineId, quantity}) => {

  const request = {quantity: quantity,}

  // TODO: rollback quantity in frontend in case of failure
  await CartAPI.updateUserCartLine(cartLineId, request, {credentials: "include"})
}

const Cart_RemoveCartLine = async (
  {state, getters, commit, dispatch},
  cartLineId) => {

  await CartAPI.removeUserCartLine(cartLineId, {credentials: "include"})
}

const Cart_RemoveCartLineList = async (
  {state, getters, commit, dispatch},
  cartLineIdList) => {

  cartLineIdList.sort() // for sequential disk access in server side :)
  const query = cartLineIdList.toString()

  await CartAPI.removeUserCartLines(query, {credentials: "include"})
}

const Auth_Login = async (
  {state, getters, commit, dispatch},
  {username, password}) => {

  const response = await AuthAPI.login({
    credentials: "include",
    headers: {"Authorization": `Basic ` + btoa(`${username}:${password}`),}
  })

  return response
}

const Auth_Register = async (
  {state, getters, commit, dispatch},
  {username, password, email, name, address}) => {

  const provider = "PASSWORD"

  const request: UserDTO = {
    provider: provider,
    username: username,
    password: password,
    email: email,
    name: name,
    address: address,
  }

  const response =  await AuthAPI.register(request, {credentials: "include"})
  return response
}

export default {
  /**
   * AUTH
   */
  [Actions.AUTH__LOGIN]: Auth_Login,
  [Actions.AUTH__REGISTER]: Auth_Register,

  /**
   * PRODUCT
   */
  [Actions.PRODUCT__FETCH_PAGINATED_ITEMS]: Product_FetchPaginatedItems,
  [Actions.PRODUCT__FETCH_CATEGORY_LIST]: Product_FetchCategoryList,

  /**
   * DETAIL
   */
  [Actions.DETAIL__FETCH_PRODUCT_ITEM]: Detail_FetchProductItem,
  [Actions.DETAIL__ADD_TO_CART]: Detail_AddToCart,

  /**
   * CART
   */
  [Actions.CART__FETCH_CART_LINE_LIST]: Cart_FetchCartLineList,
  [Actions.CART__UPDATE_CART_LINE]: Cart_UpdateCartLine,
  [Actions.CART__REMOVE_CART_LINE]: Cart_RemoveCartLine,
  [Actions.CART__REMOVE_CART_LINE_LIST]: Cart_RemoveCartLineList,
}
