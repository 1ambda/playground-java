<template>
  <div>
    <div class="hidden-md-and-up" style="margin-top: 90px;">
      &nbsp;
    </div>

    <div class="register-card-container">
      <el-card class="register-card">

        <el-row>

          <!-- register card: form area -->
          <el-col :xs="24" :sm="24" :md="14">

            <!-- title area -->
            <div class="form-title" style="margin-bottom: 15px;">
              <h1 class="form-title-text">Create your G-street Account</h1>
            </div>

            <!-- form: id -->
            <el-form class="register-form-username"
                     :label-position="'top'"
                     :hide-required-asterisk="true"
                     label-width="100px"
                     :rules="usernameFormRules"
                     :model="modelUsernameForm"
                     name="usernameForm" ref="usernameForm">
              <el-form-item class="register-form-item" prop="username">
                <label class="register-form-item-text">Username and Password</label>
                <el-input name="inputUsername" size="medium"
                          placeholder="User ID"
                          v-model="modelUsernameForm.username"></el-input>
              </el-form-item>

            </el-form>

            <!-- form: password -->
            <el-form :inline="true"
                     class="register-form-password"
                     :label-position="'top'"
                     :hide-required-asterisk="true"
                     :rules="passwordFormRules"
                     :model="modelPasswordForm"
                     name="passwordForm" ref="passwordForm">
              <el-row>
                <el-col :span="12">
                  <el-form-item class="register-form-item" prop="password">
                    <el-input name="inputPasswordConfirm"
                              placeholder="Password"
                              size="medium" type="password"
                              v-model="modelPasswordForm.password"
                              auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item class="register-form-item" prop="passwordConfirm">
                    <el-input name="inputPasswordConfirm"
                              placeholder="Confirm Password"
                              size="medium" type="password"
                              v-model="modelPasswordForm.passwordConfirm"
                              auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>

            <!-- form: information -->
            <el-form class="register-form-info"
                     :label-position="'top'"
                     label-width="100px"
                     :hide-required-asterisk="true"
                     :rules="infoFormRules"
                     :model="modelInfoForm"
                     name="infoForm" ref="infoForm">

              <el-form-item class="register-form-item" prop="email">
                <span class="register-form-item-text">Personal Information</span>
                <el-input name="inputEmail" size="medium"
                          placeholder="Email"
                          v-model="modelInfoForm.email"></el-input>
              </el-form-item>

              <el-form-item class="register-form-item" prop="name">
                <el-input name="inputName" size="medium"
                          placeholder="Your Name"
                          v-model="modelInfoForm.name"></el-input>
              </el-form-item>

              <el-form-item class="register-form-item" prop="address">
                <el-input name="inputAddress" size="medium"
                          placeholder="Location (Address)"
                          v-model="modelInfoForm.address"></el-input>
              </el-form-item>

            </el-form>

            <!-- form actions -->
            <div class="register-form-action"
                 style="text-align: left;margin-top: 10px;">
              <el-button type="text" style="margin-left: 5px;"
                         @click="handleSignInButtonClick">Sign in instead
              </el-button>
              <el-button type="primary" style="float: right;"
                         @click="handleSubmitForm">Sign up
              </el-button>
              <div class="clearfix"></div>
            </div>

            <div style="text-align: center; vertical-align: middle; margin-top: 20px;">
              <el-button type="primary" disabled plain class="login-button-facebook">
                Facebook Sign up
              </el-button>
            </div>

            <div style="text-align: center; vertical-align: middle; margin-top: 10px;">
              <el-button type="info" disabled plain class="login-button-google">
                Google Sign up
              </el-button>
            </div>


          </el-col>

          <!-- register card: image area -->
          <el-col class="hidden-sm-and-down" :md="8">
            <div class="register-image-container">
              <div class="register-image-area">
                <img src="../assets/gopher_inclusion_small.png"
                     height="120" width="120">
                <div class="register-image-text"
                     style="margin-top: 15px; padding-left: 20px; padding-right: 20px;">
                  By clicking "Sign up",
                  you agree to our terms of service and privacy statement.
                  We’ll occasionally send you account related emails.
                </div>
              </div>
            </div>
          </el-col>

          <el-col class="hidden-md-and-up" :sm="24">
            <div class="register-image-container-less-md"
                 style="margin-top: 50px;">
              <div class="register-image-area-less-md">
                <img src="../assets/gopher_inclusion_small.png"
                     height="120" width="120">
                <div class="register-image-text"
                     style="margin-top: 15px; padding-left: 20px; padding-right: 20px;">
                  By clicking "Sign up",
                  you agree to our terms of service and privacy statement.
                  We’ll occasionally send you account related emails.
                </div>
              </div>
            </div>
          </el-col>

        </el-row>
      </el-card>
    </div>

    <div class="hidden-md-and-up" style="margin-bottom: 110px;">
      &nbsp;
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Mixins, Vue} from "vue-property-decorator"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import * as Actions from "@/store/action_type.ts"
  import Alert from "@/components/Alert.vue"
  import * as RouteType from "@/store/route_type.ts"

  @Component({components: {}})
  export default class Register extends Mixins(Alert) {
    public $refs: any
    public $router: any
    public $store: any

    private modelUsernameForm = {
      username: "",
    }

    private modelPasswordForm = {
      password: "",
      passwordConfirm: "",
    }

    private modelInfoForm = {
      name: "",
      address: "",
      email: "",
    }

    private passwordFormRules = {
      password: [
        {validator: this.validatePassword, trigger: "blur"},
      ],
      passwordConfirm: [
        {validator: this.validatePasswordConfirm, trigger: "blur"},
      ],
    }

    private usernameFormRules = {
      username: [
        {required: true, message: "Please insert id", trigger: "blur"},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
        {pattern: /^([a-zA-Z0-9]+)$/, message: "Please use alpha numeric only", trigger: "blur"}],
    }

    private infoFormRules = {
      email: [
        {required: true, message: "Please insert email address", trigger: "blur"},
        {type: "email", message: "Please input correct email address", trigger: ["blur", "change"]},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
      ],
      name: [
        {required: true, message: "Please insert name", trigger: "blur"},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
        {pattern: /^([a-zA-Z]+)$/, message: "Please use alphabet only", trigger: "blur"},
      ],
      address: [
        {required: true, message: "Please insert address", trigger: "blur"},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
        {pattern: /^([a-zA-Z]+)$/, message: "Please use alphabet only", trigger: "blur"},
      ],
    }

    /**
     * vuex mappers and computed properties
     */
    @Action(Actions.AUTH__REGISTER) actionRegister

    /**
     * life-cycle methods
     */

    /**
     * event handlers
     */

    validatePassword(rule, value, callback) {
      if (value === "") {
        callback(new Error("Please insert password"))
      } else if (value.length < 4 || value.length > 30) {
        callback(new Error("Length should be 4 to 30"))
      } else {
        if (this.modelPasswordForm.password !== "") {
          this.$refs.passwordForm.validateField("passwordConfirm")
        }
        callback()
      }
    };

    validatePasswordConfirm(rule, value, callback) {
      if (value === "") {
        callback(new Error("Please insert password again"))
      } else if (value !== this.modelPasswordForm.password) {
        callback(new Error("Password doesn't match with the previous one."))
      } else {
        callback()
      }
    }

    public handleSubmitForm() {
      this.validateForms().then(() => {
        const request = {
          username: this.modelUsernameForm.username,
          password: this.modelPasswordForm.password,
          email: this.modelInfoForm.email,
          name: this.modelInfoForm.name,
          address: this.modelInfoForm.address,
        }

        this.actionRegister(request)
          .then((response) => {
            this.displaySuccessAlert(`Created your account "${response.username}". Please login.`)
            this.$router.push("/login")
          })

      }).catch(() => {
        // handle form validation failure
        this.displayErrorAlert("Validation failed. Please insert required values.")
      })
    }

    handleSignInButtonClick() {
      this.$router.push(`/${RouteType.LOGIN}`)
    }

    public resetForm() {
      this.$refs.usernameForm.resetFields()
      this.$refs.passwordForm.resetFields()
      this.$refs.infoForm.resetFields()
    }

    /**
     * helpers
     */

    public validateForm(formName: string): Promise<any> {
      const promise = new Promise((resolve, reject) => {
        this.$refs[formName].validate((valid: any) => {
          if (!valid) {
            reject(valid)
            return
          }
        })

        resolve()
      })

      return promise
    }

    public validateForms(): Promise<any> {
      return this.validateForm("usernameForm")
        .then(() => {
          return this.validateForm("passwordForm")
        })
        .then(() => {
          return this.validateForm("infoForm")
        })
    }

  }
</script>

<style lang="scss">
  .register-card-container {
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    width: 100%;
    height: 85vh; /* need to define an explicit height! */
  }

  .register-card {
    width: 600px;
  }

  .register-form-username {
    text-align: left;
  }

  .register-form-password {
    text-align: left;
  }

  .register-form-info {
    text-align: left;
  }

  .register-form-item .el-form-item__content {
    line-height: 23px;
  }

  .register-form-item-text {
    line-height: 30px;
    color: #606266;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", Arial, sans-serif;
    font-size: 15px;
    padding-left: 2px;
  }

  .register-form-action {
  }

  .register-image-container {
    width: auto;
    display: block;
  }

  .register-image-area {
    margin-top: 140px;
    margin-right: -33.33%;
  }

  .register-image-text {
    color: #606266;
    font-size: 15px;
  }

  .form-title {
    text-align: left;
  }

  .form-title-text {
    font-size: 20px;
    font-weight: 500;
    margin-top: 5px;
  }

  .register-form-item.el-form-item {
    margin-bottom: 20px;
  }

</style>