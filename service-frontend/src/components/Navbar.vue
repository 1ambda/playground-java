<template>
  <div>
    <div v-show="getterIsAuthenticated">
      <el-menu :router="true"
               :default-active="$route.name"
               mode="horizontal" menu-trigger="click"
               text-color="#303133" class="navbar-menu">

        <!-- left aligned image menu -->
        <el-menu-item index="home" :route="getRoute('home')"
                      class="navbar-menu-item">
          <img src="../assets/gopher_inclusion_small.png"
               height="50" width="50" style="padding-left: 7px; padding-right: 2px;">
        </el-menu-item>

        <el-menu-item index="product" :route="getRoute('product')"
                      class="navbar-menu-item">
          <span class="navbar-menu-item-text">Product</span>
        </el-menu-item>

        <!-- right aligned dropdown menu -->
        <el-submenu index="nav-dropdown"
                    class="navbar-username"
                    popper-class="navbar-username-popper"
                    style="float: right;">
          <template slot="title">
            <img src="../assets/anonymous_avatar.png" height="25" width="25"
                 class="navbar-username-avatar">
            <span class="navbar-username-text">{{ stateUsername }}</span>
          </template>
          <el-menu-item index="nav-dropdown-setting"
                        class="navbar-submenu-item">
            <i class="el-icon-menu navbar-submenu-item-icon"></i>
            <span class="navbar-submenu-item-text">Settings</span>
          </el-menu-item>
          <el-menu-item index="nav-dropdown-logout" v-on:click="handleLogoutClick"
                        class="navbar-submenu-item">
            <i class="el-icon-circle-close navbar-submenu-item-icon"></i>
            <span class="navbar-submenu-item-text">Logout</span>
          </el-menu-item>
        </el-submenu>

        <el-menu-item index="cart" :route="getRoute('cart')" style="float: right;"
                      class="navbar-menu-item navbar-menu-item-last">
          <span class="navbar-menu-item-text ">
            Cart
          </span>
        </el-menu-item>
      </el-menu>
    </div>

    <div v-show="!getterIsAuthenticated">
      <el-menu :router="true"
               :default-active="$route.name"
               mode="horizontal" menu-trigger="click"
               text-color="#303133" class="navbar-menu">
        <!-- left aligned image menu -->
        <el-menu-item index="home" :route="getRoute('home')"
                      class="navbar-menu-item">
          <img src="../assets/gopher_inclusion_small.png"
               height="50" width="50" style="padding-left: 7px; padding-right: 2px;">
        </el-menu-item>

        <el-menu-item index="register" :route="getRoute('register')"
                      class="navbar-menu-item">
          <span class="navbar-menu-item-text">Register</span>
        </el-menu-item>

        <el-menu-item index="login" :route="getRoute('login')"
                      class="navbar-menu-item">
          <span class="navbar-menu-item-text">Login</span>
        </el-menu-item>

        <span style="float: right; margin-left: 30px; margin-right: 30px;"
              class="navbar-menu-item">
          <a href="https://github.com/1ambda" target="_blank"
             class="github-link"
             style="display: block; text-decoration: none;">
            <img src="../assets/github.svg" class="github-icon" height="24" width="24"
                 style="margin-bottom: -6px; margin-right: 4px;">
            <span class="navbar-menu-item-text"
                  style="vertical-align: middle;">
              Github
            </span>
          </a>
        </span>

        <el-menu-item index="about" :route="getRoute('about')" style="float: right;"
                      class="navbar-menu-item">
          <span class="navbar-menu-item-text">About</span>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Mixins, Vue} from "vue-property-decorator"
  import {mapActions, mapGetters, mapMutations, mapState} from "vuex"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import * as Getters from "@/store/getter_type"
  import * as RouteType from "@/store/route_type"

  import {Routes,} from "@/router.ts"
  import {AuthAPI} from "@/common/auth.service.ts"
  import Alert from "@/components/Alert.vue"
  import {Failure,} from "@/generated/swagger"
  import {handleFailure} from "../common/failure.util"

  @Component({
    components: {},
    watch: {
      [States.AUTH__FLASH_MESSAGE](newMessage, oldMessage) {
        if (!newMessage) {
          return
        }

        this.commitClearFlashMessage()
        this.displayErrorAlert(newMessage)
      },
    },
  })
  export default class Navbar extends Mixins(Alert) {
    public routes = Routes
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
        setTimeout(() => {
          const alertOffsetFromTop = 70
          this.displaySuccessAlert("Successfully Logged-out.", alertOffsetFromTop)
        }, 500)

        this.commitLogout()
        this.$router.push(`/${RouteType.LOGIN}`)
      }).catch(handleFailure)
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
  .github-link:visited {
    color: #303133;
  }

  .github-icon {
    fill: white;
  }

  .navbar-menu {
    height: 74px;
  }

  .navbar-menu-item {
    height: 74px;
    line-height: 74px;
  }

  .navbar-menu-item-last {
    border-right: solid 1px #e6e6e6;
    padding-right: 40px;
  }

  .navbar-menu-item-text {
    font-size: 18px;
  }

  .navbar-submenu-item {
  }

  .navbar-submenu-item-icon {
    padding-left: 10px;
  }

  .navbar-submenu-item-text {
    padding-left: 5px;
  }

  .navbar-username {
    height: 74px;
    line-height: 74px;
    padding-top: 7px;
    margin-left: 10px;
    margin-right: 10px;
  }

  .navbar-username-text {
    font-size: 16px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  }

  .navbar-username-avatar {
    margin-right: 12px;
    border-radius: 50%;
    object-fit: cover;
    border: 1px solid rgb(255, 255, 255);
    background-color: rgb(242, 242, 242) !important;
    box-shadow: rgb(235, 235, 235) 0px 0px 0px 2px !important;
  }

</style>