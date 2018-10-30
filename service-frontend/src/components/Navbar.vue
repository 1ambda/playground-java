<template>
  <div>
    <div v-show="getterIsAuthenticated">
      <el-menu :router="true"
               mode="horizontal"
               background-color="#545c64"
               text-color="#fff"
               :default-active="$route.name"
               active-text-color="#ffd04b">

        <!-- left aligned image menu -->
        <el-menu-item index="home" :route="getRoute('home')">
          <img src="../assets/gopher-front.svg" height="28" width="28" style="margin-right: 3px;">
        </el-menu-item>

        <el-menu-item index="product" :route="getRoute('product')">
          <span>Product</span>
        </el-menu-item>

        <!-- right aligned dropdown menu -->
        <el-submenu index="nav-dropdown" style="float: right;">
          <template slot="title"><i class="el-icon-menu"></i><span>{{ stateUsername }}</span></template>
          <el-menu-item index="nav-dropdown-setting">
            <i class="el-icon-setting"></i>
            <span>Settings</span>
          </el-menu-item>
          <el-menu-item index="nav-dropdown-logout" v-on:click="handleLogoutClick">
            <i class="el-icon-circle-close"></i>
            <span>Logout</span>
          </el-menu-item>
        </el-submenu>

        <el-menu-item index="cart" :route="getRoute('cart')" style="float: right;">
          <span>Cart</span>
        </el-menu-item>
      </el-menu>
    </div>

    <div v-show="!getterIsAuthenticated">
      <el-menu :router="true"
               mode="horizontal"
               background-color="#545c65"
               text-color="#fff"
               :default-active="$route.name"
               active-text-color="#ffd04b">
        <el-menu-item index="register" :route="getRoute('register')">
          <span>Register</span>
        </el-menu-item>

        <el-menu-item index="login" :route="getRoute('login')">
          <span>Login</span>
        </el-menu-item>

        <el-menu-item index="github" style="float: right;">
          <a href="https://github.com/1ambda" target="_blank" style="display: block; text-decoration: none;">
            <img src="../assets/github.svg" class="github-icon" height="24" width="24"
                 style="margin-bottom: 2px; margin-right: 5px;">
            <span>Github</span>
          </a>
        </el-menu-item>

        <el-menu-item index="about" :route="getRoute('about')" style="float: right;">
          <span>About</span>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import {mapActions, mapGetters, mapMutations, mapState} from "vuex"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import * as Getters from "@/store/getter_type"
  import * as Routes from "@/store/route_type"

  import Router from "@/router.ts"
  import {AuthAPI} from "@/common/auth.service.ts"
  import {Failure,} from "@/generated/swagger"
  import {handleFailure} from "../common/failure.util"

  @Component({
    components: {},
    watch: {
      [States.AUTH__FLASH_MESSAGE](newMessage, oldMessage) {
        if (!newMessage) {
          return
        }

        this.$notify.error({
          title: `Error`,
          message: newMessage,
        })

        this.commitClearFlashMessage()
      },
    },
  })
  export default class Navbar extends Vue {
    public routes = Router
    public $notify: any
    public $route: any
    public $router: any
    public $store: any

    public defaultRouteIndex = ""
    public routeIndexMap = {}

    /**
     * vuex mappers and computed properties
     */
    @Mutation(Mutations.AUTH__LOGOUT) commitLogout
    @Mutation(Mutations.AUTH__CLEAR_FLASH_MESSAGE) commitClearFlashMessage

    @State(States.AUTH__USERNAME) stateUsername
    @State(States.AUTH__FLASH_MESSAGE) stateFlashMessage

    @Getter(Getters.AUTH__AUTHENTICATED) getterIsAuthenticated

    /**
     * life-cycle methods
     */
    created() {
      const name = this.$route.name
      this.defaultRouteIndex = name

      this.routeIndexMap = this.routes.reduce((result, route) => {
        const name = route.name
        result[name] = route
        return result
      }, {})
    }

    /**
     * event handlers
     */

    public handleLogoutClick() {
      AuthAPI.logout({credentials: "include"}).then((response) => {
        this.$notify({
          title: "Logout",
          message: "Successfully Logged-out.",
          type: "success",
        })

        this.commitLogout()
        this.$router.push(`/${Routes.LOGIN}`)
      }).catch(handleFailure(this.$notify, this.$router, this.$store))
    }

    /**
     * helpers
     */

    public getRoute(name) {
      const route = this.routeIndexMap[name]
      return route
    }

    public shouldDisplayRoute(name) {
      const route = this.routeIndexMap[name]
      return this.shouldDisplay(route.requiresAuth, route.authenticated, route.common)
    }

    public shouldDisplay(requiresAuth: boolean, authenticated: boolean, common: boolean) {
      if (common) {
        return true
      } else if (requiresAuth && authenticated) {
        return true
      } else if (!requiresAuth && !authenticated) {
        return true
      }

      return false
    }

  }
</script>

<style scoped>
  .github-icon {
    fill: white;
  }
</style>