<script lang="ts">
  import Vue from "vue"
  import Component from "vue-class-component"

  // You can declare a mixin as the same style as components.
  @Component
  export default class Alert extends Vue {
    public $notify: any
    public $createElement: any

    defaultOffset = 70

    commonStyle = "font-size: 15px; text-align: start;"

    displaySuccessAlert(message: string, offset?: number) {
      if (!offset) {
        offset = this.defaultOffset
      }

      const h = this.$createElement

      const format = h("div", {style: this.commonStyle}, message)

      this.$notify({
        offset: offset,
        message: format,
        type: "success",
        customClass: "global-alert-success",
      })
    }

    displayInfoAlert(message: string, offset?: number) {
      if (!offset) {
        offset = this.defaultOffset
      }

      const h = this.$createElement

      const format = h("div", {style: this.commonStyle}, message)

      this.$notify({
        offset: offset,
        message: format,
        type: "info",
        customClass: "global-alert-info",
      })
    }

    displayWarningAlert(message: string, offset?: number) {
      if (!offset) {
        offset = this.defaultOffset
      }

      const h = this.$createElement

      const format = h("div", {style: this.commonStyle}, message)

      this.$notify({
        offset: offset,
        message: format,
        type: "warning",
        customClass: "global-alert-warning",
      })
    }

    displayErrorAlert(message: string, offset?: number) {
      if (!offset) {
        offset = this.defaultOffset
      }

      const h = this.$createElement

      const format = h("div", {style: this.commonStyle}, message)

      this.$notify({
        offset: offset,
        message: format,
        type: "error",
        customClass: "global-alert-error",
      })
    }
  }

</script>

<style>
  .el-notification__closeBtn {
    top: 12px;
    right: 8px;
  }

  .global-alert-success {
    border-left: 3px solid #67C23A;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-alert-success > i.el-icon-success {
    font-size: 18px;
    margin-top: 7px;
    color: #67C23A;
  }

  .global-alert-success > div.el-notification__group {
    margin-left: 5px;
  }

  .global-title-alert-success {
    border-left: 3px solid #67C23A;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-title-alert-success > i.el-icon-success {
    font-size: 18px;
    color: #67C23A;
  }

  .global-title-alert-success > div.el-notification__group {
    margin-left: 5px;
  }

  .global-alert-info {
    border-left: 3px solid #409EFF;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-alert-info > i.el-icon-info {
    font-size: 18px;
    margin-top: 7px;
    color: #409EFF;
  }

  .global-alert-info > div.el-notification__group {
    margin-left: 5px;
  }

  .global-title-alert-info {
    border-left: 3px solid #409EFF;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-title-alert-info > i.el-icon-info {
    font-size: 18px;
    color: #409EFF;
  }

  .global-title-alert-info > div.el-notification__group {
    margin-left: 5px;
  }


  .global-alert-warning {
    border-left: 3px solid #E6A23C;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-alert-warning > i.el-icon-warning {
    font-size: 18px;
    margin-top: 7px;
    color: #E6A23C;
  }

  .global-alert-warning > div.el-notification__group {
    margin-left: 5px;
  }

  .global-title-alert-warning {
    border-left: 3px solid #E6A23C;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-title-alert-warning > i.el-icon-warning {
    font-size: 18px;
    color: #E6A23C;
  }

  .global-title-alert-warning > div.el-notification__group {
    margin-left: 5px;
  }

  .global-alert-error {
    border-left: 3px solid #F56C6C;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-alert-error > i.el-icon-error {
    font-size: 18px;
    margin-top: 7px;
    color: #F56C6C;
  }

  .global-alert-error > div.el-notification__group {
    margin-left: 5px;
  }

  .global-title-alert-error {
    border-left: 3px solid #F56C6C;
    border-bottom-left-radius: 3px;
    border-top-left-radius: 3px;
  }

  .global-title-alert-error > i.el-icon-error {
    font-size: 18px;
    color: #F56C6C;
  }

  .global-title-alert-error > div.el-notification__group {
    margin-left: 5px;
  }
</style>
