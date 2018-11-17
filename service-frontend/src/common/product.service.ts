import {AuthControllerApi, CartControllerApi, CatalogControllerApi} from "@/generated/swagger"
import * as Endpoint from "@/common/endpoint.service.ts"

const endpoint = Endpoint.REST

const catalogAPI = new CatalogControllerApi(undefined, endpoint)
const cartAPI = new CartControllerApi(undefined, endpoint)
const authAPI = new AuthControllerApi(undefined, endpoint)

export const CatalogAPI = catalogAPI
export const CartAPI = cartAPI
export const AuthAPI = authAPI

