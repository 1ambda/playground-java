<template>
  <el-row type="flex" justify="center">
    <el-col :xs="16" :sm="12" :md="8" :lg="6">
      <div style="margin: 40px;"></div>
      <el-form :label-position="'right'" :rules="rules" label-width="100px" :model="ruleForm" ref="ruleForm">
        <el-form-item label="ID" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">Login</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import {mapActions, mapGetters, mapMutations, mapState} from "vuex"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import {AuthAPI} from "@/common/auth.service.ts"
  import {handleFailure} from "../common/failure.util"

  @Component({
    components: {},
  })
  export default class Login extends Vue {
    public $refs: any
    public $notify: any
    public $router: any
    public $store: any

    public ruleForm = {
      username: "",
      password: "",
    }

    public rules = {
      username: [
        {required: true, message: "Please input id", trigger: "blur"},
        {min: 4, max: 30, message: "Length should be 4 to 30", trigger: "blur"},
        {pattern: /^([a-zA-Z0-9]+)$/, message: "Please use alpha numeric only", trigger: "blur"}],
      password: [
        {required: true, message: "Please input password", trigger: "blur"}],
    }

    /**
     * vuex mappers and computed properties
     */

    @Mutation(Mutations.AUTH__LOGIN) commitLogin
    @State(States.AUTH__USERNAME) stateUsername

    /**
     * life-cycle methods
     */

    public mounted() {
      if (this.stateUsername !== "") {
        this.$router.push("/")
        return
      }
    }

    /**
     * event handlers
     */

    public submitForm(formName: string) {
      this.$refs[formName].validate((valid: any) => {
        if (!valid) {
          this.$notify.warn({
            title: `Validation Failed`,
            message: "Please insert required values",
          })
          return
        }

        let username = this.ruleForm.username
        let password = this.ruleForm.password

        AuthAPI.login({
          credentials: "include",
          headers: {"Authorization": `Basic ` + btoa(`${username}:${password}`),}
        }).then((response) => {
          if (!response || !response.username) {
            this.$notify.warn({
              title: `Invalid Response`,
              message: "Can't login",
            })
            return
          }

          this.commitLogin(response.username)
          this.$router.push("/")
        }).catch(handleFailure)
      })
    }

    /**
     * helpers
     */
  }
</script>
