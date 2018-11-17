import {AuthControllerApi as AuthSwaggerApi, Configuration, ConfigurationParameters} from '@/generated/swagger'
import * as Endpoint from "@/common/endpoint.service.ts"

const endpoint = Endpoint.REST

const authAPI = new AuthSwaggerApi(undefined, endpoint)

export const AuthAPI = authAPI

export const AuthApiWithSecurity = function (username: string,
                                             password: string) {

  let param: ConfigurationParameters = {
    username: username,
    password: password,
    basePath: endpoint,
  }
  let config = new Configuration(param)
  return new AuthSwaggerApi(config)
}









