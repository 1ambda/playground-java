<template>
  <div>
    <!-- price filter -->
    <el-popover placement="bottom-start"
                width="340" trigger="click">
      <div style="padding-left: 20px; padding-right: 20px;">

        <!-- price filter popover title -->
        <div class="price-filter-popover-title" style="margin-top: 20px; margin-left: -10px;">
          Price Range
        </div>

        <!-- price filter popover slider -->
        <div style="margin-top: 10px; padding-left: 2px; padding-right: 2px;">
          <el-slider v-model="priceFilterSliderValues"
                     :max="priceFilterSliderMax" :min="priceFilterSliderMin" range></el-slider>
        </div>
        <div class="price-filter-popover-slider-text" style="margin-top: 10px; margin-bottom: 10px;">
          <span>₩</span>
          <span>{{ priceFilterSliderValues[0] }}</span>
          <span> - </span>
          <span>₩</span>
          <span>{{ priceFilterSliderValues[1] }}{{ priceFilterSliderValues[1] === priceFilterSliderMax ? '+' : '' }}</span>
        </div>

        <!-- price filter popover button -->
        <div style="text-align: right; margin: 0">
          <el-button type="text" size="mini" @click="visible2 = false">Cancel</el-button>
          <el-button type="primary" size="mini" @click="visible2 = false">Apply</el-button>
        </div>
      </div>

      <!-- price filter button -->
      <el-button slot="reference" type="primary" plain>Price</el-button>
    </el-popover>

    <!-- tag filter -->
    <span style="margin-left: 10px;">
            <el-button type="primary" plain>Rate</el-button>
          </span>

    <!-- arrival filter -->
    <span style="margin-left: 10px;">
            <el-button type="primary" plain>Arrival</el-button>
          </span>

    <!-- tag filter -->
    <span style="margin-left: 10px;">
            <el-button type="primary" plain>Tag</el-button>
          </span>

    <!-- TODO: delivery date filter -->
    <span style="margin-left: 10px;">
            <el-button type="primary" plain>Date</el-button>
          </span>
  </div>
</template>
<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import {State, Getter, Action, Mutation,} from 'vuex-class'
  import {handleFailure} from "../common/failure.util";

  @Component({
    name: "ProductFilter",
  })
  export default class ProductFilter extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    public priceFilterSliderValues = [0, 100000]
    public priceFilterSliderMin = 0
    public priceFilterSliderMax = 100000

    /**
     * vuex mappers and computed properties
     */

    @Mutation(Mutations.PRODUCT__UPDATE_FILTER_PRICE) updateProductFilterPrice
    @Mutation(Mutations.PRODUCT__RESET_FILTER_PRICE) resetProductFilterPrice
    @State(States.PRODUCT__FILTER_MAX_PRICE) filterMinPrice
    @State(States.PRODUCT__FILTER_MAX_PRICE) filterMaxPrice

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
