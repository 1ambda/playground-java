<script lang="ts">
  import Vue from "vue"
  import Component from "vue-class-component"

  // You can declare a mixin as the same style as components.
  @Component
  export default class Alert extends Vue {
    public $notify: any
    public $createElement: any

    displayInfoAlert(message: string) {
      const h = this.$createElement

      const format = h("div", {style: "font-size: 15px;"}, message)

      this.$notify({
        message: format,
        type: "info",
        customClass: "global-alert-info",
      })
    }
  }

</script>

<style>
  .global-alert-info {
    border-left: 2px solid #409EFF;
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

</style>
