import {AuthControllerApi as AuthSwaggerApi, Configuration, ConfigurationParameters} from '@/generated/swagger'

const endpoint = process.env.VUE_APP_GATEWAY_ENDPOINT

console.log(endpoint)

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









