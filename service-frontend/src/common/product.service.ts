import {AuthControllerApi, CartControllerApi, CatalogControllerApi} from '@/generated/swagger'

const endpoint = process.env.VUE_APP_GATEWAY_ENDPOINT

const catalogAPI = new CatalogControllerApi(undefined, endpoint)
const cartAPI = new CartControllerApi(undefined, endpoint)
const authAPI = new AuthControllerApi(undefined, endpoint)

export const CatalogAPI = catalogAPI
export const CartAPI = cartAPI

