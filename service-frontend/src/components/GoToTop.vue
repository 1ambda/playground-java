<template>
  <back-to-top :visibleoffset="computedOffset">
    <div class="backToTop">
      <i class="el-icon-caret-top"></i>
    </div>
  </back-to-top>
</template>
<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'
  import {State, Getter, Action, Mutation,} from 'vuex-class'

  @Component({
    name: 'GoToTop',
    props: {
      visibleoffset: Number,
    }
  })
  export default class GoToTop extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    public visibleoffset

    /**
     * vuex mappers and computed properties
     */

    get computedOffset() {
      const defaultOffset = 100
      if (!this.visibleoffset) {
        return defaultOffset
      }

      return this.visibleoffset
    }

    /**
     * life-cycle methods
     */

    /**
     * event handlers
     */

    /**
     * helpers
     */
  }

</script>

<style lang="scss" scoped>
  .backToTop {
    background-color: #fff;
    position: fixed;
    right: 120px;
    bottom: 215px;
    width: 40px;
    border-radius: 20px;
    cursor: pointer;
    transition: .3s;
    box-shadow: 0 0 6px rgba(0, 0, 0, .12);
    z-index: 5;
    i {
      color: #409EFF;
      display: block;
      line-height: 40px;
      text-align: center;
      font-size: 18px;
    }
    &.hover {
      opacity: 1;
    }
  }
</style>
