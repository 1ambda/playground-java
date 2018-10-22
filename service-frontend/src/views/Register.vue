<template>
  <el-row type="flex" justify="center">
    <el-col :xs="16" :sm="12" :md="8" :lg="6">
      <div style="margin: 20px;"></div>
      <el-form :label-position="'right'" :rules="rules" label-width="100px" :model="ruleForm" ref="ruleForm">
        <el-form-item label="ID" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>

        <el-form-item label="Name" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>

        <el-form-item label="Address" prop="address">
          <el-input v-model="ruleForm.address"></el-input>
        </el-form-item>

        <el-form-item label="Email" prop="email">
          <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>

        <el-form-item label="Password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">Create</el-button>
          <el-button @click="resetForm('ruleForm')">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'
  import {AuthAPI} from '@/common/auth.service.ts'
  import {UserDTO} from '../generated/swagger'
  import {handleFailure} from "../common/failure.util";

  @Component({components: {}})
  export default class Register extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    private ruleForm = {
      username: '',
      name: '',
      address: '',
      email: '',
      password: '',
    }

    private rules = {
      username: [
        {required: true, message: 'Please insert id', trigger: 'blur'},
        {min: 4, max: 30, message: 'Length should be 4 to 30', trigger: 'blur'},
        {pattern: /^([a-zA-Z0-9]+)$/, message: 'Please use alpha numeric only', trigger: 'blur'}],
      email: [
        {required: true, message: 'Please insert email address', trigger: 'blur'},
        {type: 'email', message: 'Please input correct email address', trigger: ['blur', 'change']},
        {min: 4, max: 30, message: 'Length should be 4 to 30', trigger: 'blur'},
      ],
      password: [
        {required: true, message: 'Please insert password', trigger: 'blur'},
      ],
      name: [
        {required: true, message: 'Please insert name', trigger: 'blur'},
        {min: 4, max: 30, message: 'Length should be 4 to 30', trigger: 'blur'},
        {pattern: /^([a-zA-Z]+)$/, message: 'Please use alphabet only', trigger: 'blur'},
      ],
      address: [
        {required: true, message: 'Please insert address', trigger: 'blur'},
        {min: 4, max: 30, message: 'Length should be 4 to 30', trigger: 'blur'},
        {pattern: /^([a-zA-Z]+)$/, message: 'Please use alphabet only', trigger: 'blur'},
      ],
    }

    public submitForm(formName: string) {
      this.$refs[formName].validate((valid: any) => {
        if (!valid) {
          this.$notify({
            title: `Validation Failed`,
            message: 'Please insert required values',
            type: 'warning',
          })
          return
        }

        const request: UserDTO = {
          provider: "PASSWORD",
          username: this.ruleForm.username,
          password: this.ruleForm.password,
          email: this.ruleForm.email,
          name: this.ruleForm.name,
          address: this.ruleForm.address,
        }

        AuthAPI.register(
          request,
          {credentials: 'include'}
        ).then((response) => {
          this.$notify({
            title: 'Success',
            message: `Created ${response.username}`,
            type: 'success',
          })
          this.$router.push('/login')
        }).catch(handleFailure(this.$notify))
      })
    }

    public resetForm(formName: string) {
      this.$refs[formName].resetFields()
    }
  }
</script>