<template>
  <div class="login-card-container">
    <el-card class="login-card" style="padding-bottom: 5px;">

      <!-- oauth login -->
      <div slot="header">
        <div style="text-align: center; vertical-align: middle; margin-top: 8px;">
          <img src="../assets/gopher_sail.png"
               height="80" width="80" class="login-image">
        </div>
        <div style="text-align: center; vertical-align: middle; margin-top: 18px;">
          <el-button type="primary" disabled plain class="login-button-facebook">
            Facebook Login
          </el-button>
        </div>

        <div style="text-align: center; vertical-align: middle; margin-top: 10px;">
          <el-button type="info" disabled plain class="login-button-google">
            Google Login
          </el-button>
        </div>

        <div style="text-align: center; margin-top: 9px;">
          Or
        </div>
      </div>

      <!-- username login -->
      <div>
        <el-form :label-position="'top'"
                 :rules="rules"
                 :hide-required-asterisk="true"
                 label-width="100px"
                 :model="modelForm" ref="loginForm">
          <el-form-item class="login-form-item" prop="username">
            <span>
              <label class="login-label">Username</label>
            </span>
            <el-input v-model="modelForm.username"></el-input>
          </el-form-item>
          <el-form-item class="login-form-item" prop="password">
            <span>
              <label class="login-label">Password</label>
              <el-button type="text" class="login-button-forget-password"
                         style="float: right;">Forget Password?</el-button>
            </span>
            <el-input type="password" v-model="modelForm.password" auto-complete="off"></el-input>
          </el-form-item>

          <div class="login-form-action"
               style="margin-top: 5px; text-align: left">
            <el-button type="text"
                       @click="handleClickRegister">Create Account
            </el-button>
            <el-button type="primary" style="float: right;"
                       @click="handleSubmitForm('loginForm')">Login
            </el-button>
            <div class="clearfix"></div>
          </div>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
  import {Component, Mixins, Vue} from "vue-property-decorator"
  import * as Actions from "@/store/action_type"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import * as RouteType from "@/store/route_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import Alert from "@/components/Alert.vue"
  import {Notification} from "element-ui"

  @Component({
    components: {},
  })
  export default class Login extends Mixins(Alert) {
    public $refs: any
    public $router: any
    public $store: any

    public modelForm = {
      username: "",
      password: "",
    }

    public rules = {
      username: [
        {required: true, message: "Please insert username", trigger: "blur"},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
        {pattern: /^([a-zA-Z0-9]+)$/, message: "Please use alpha numeric only", trigger: "blur"}],
      password: [
        {required: true, message: "Please insert password", trigger: "blur"}],
    }

    /**
     * vuex mappers and computed properties
     */

    @Mutation(Mutations.AUTH__LOGIN) commitLogin
    @State(States.AUTH__USERNAME) stateUsername
    @Action(Actions.AUTH__LOGIN) actionLogin

    /**
     * life-cycle methods
     */

    public mounted() {
      if (this.stateUsername !== "") {
        this.$router.push(`/${RouteType.HOME}`)
        return
      }
    }

    /**
     * event handlers
     */

    public handleSubmitForm(formName: string) {
      this.$refs[formName].validate((valid: any) => {
        if (!valid) {

          this.displayErrorAlert("Validation failed. Please insert required values.")
          return
        }

        let username = this.modelForm.username
        let password = this.modelForm.password

        this.actionLogin({username, password})
          .then((response) => {
            if (!response || !response.username) {
              this.displayErrorAlert("Can't proceed. Please re-try.")
              return
            }

            this.commitLogin(response.username)
            this.$router.push(`/${RouteType.HOME}`)
            this.closeAllAlerts()
          })
      })
    }

    public handleClickRegister() {
      this.$router.push(`/${RouteType.REGISTER}`)
    }

    /**
     * helpers
     */
  }
</script>

<style lang="scss">

  .login-card-container {
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    width: 100%;
    height: 85vh; /* need to define an explicit height! */
  }

  .login-card {
    width: 400px;
  }

  .login-form-item {
    text-align: left;
  }

  .login-label {
    font-size: 15px;
    padding-bottom: 9px;
    color: #00356b;
  }

  .login-button-forget-password {
    color: #E6A23C;
    font-size: 13px;
    font-weight: 400;
  }

  .login-button-forget-password:hover, .login-button-forget-password:visited{
    color: #eab35f;
  }

  .login-button-facebook, .login-button-google {
    width: 100%;
  }

  .login-form-container .el-card .el-card__header {
    padding-bottom: 5px;
  }

  .login-image {
    border-radius: 95%;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 1px;
  }

</style>