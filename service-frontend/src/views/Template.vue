<template>

</template>
<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'
  import {State, Getter, Action, Mutation,} from 'vuex-class'
  import {handleFailure} from "../common/failure.util";

  @Component({
    components: {},
  })
  export default class Template extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    /**
     * vuex mappers and computed properties
     */

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

<style scoped>

</style>
