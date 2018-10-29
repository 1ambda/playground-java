<template>
  <div>
    <!-- price filter -->
    <el-popover placement="bottom-start"
                width="340" trigger="click"
                v-model="priceFilterPopoverEnable">
      <div style="padding-left: 20px; padding-right: 20px;">

        <!-- price filter popover title -->
        <div class="price-filter-popover-title" style="margin-top: 20px; margin-left: -10px;">
          Price Range
        </div>

        <!-- price filter popover slider -->
        <div style="margin-top: 10px; padding-left: 2px; padding-right: 2px;">
          <el-slider v-model="priceFilterSliderValues"
                     :max="priceFilterSlideInitialMax" :min="priceFilterSlideInitialMin"
                     range
                     @change="handlePriceSliderChange"></el-slider>
        </div>
        <div class="price-filter-popover-slider-text" style="margin-top: 10px; margin-bottom: 10px;">
          <span>{{ buildPriceRangeText() }}</span>
        </div>

        <!-- price filter popover button -->
        <div style="text-align: right; margin: 0">
          <el-button type="text" size="mini" @click="handleFilterCancel">Cancel</el-button>
          <el-button type="primary" size="mini" @click="handleFilterApply">Apply</el-button>
        </div>
      </div>

      <!-- price filter button -->
      <el-button v-if="!isPriceFilterApplied" slot="reference" type="primary" plain>
        Price
      </el-button>
      <el-button v-if="isPriceFilterApplied" slot="reference" type="primary">
        <span>{{ buildPriceRangeText() }}</span>
      </el-button>
    </el-popover>

    <!-- tag filter -->
    <span style="margin-left: 10px;">
      <el-button disabled type="primary" plain>Rate</el-button>
    </span>

    <!-- arrival filter -->
    <span style="margin-left: 10px;">
      <el-button disabled type="primary" plain>Arrival</el-button>
    </span>

    <!-- tag filter -->
    <span style="margin-left: 10px;">
      <el-button disabled type="primary" plain>Tag</el-button>
    </span>

    <!-- TODO: delivery date filter -->
    <span style="margin-left: 10px;">
      <el-button disabled type="primary" plain>Date</el-button>
    </span>
  </div>
</template>
<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"

  @Component({
    name: "ProductFilter",
  })
  export default class ProductFilter extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    public priceFilterSliderValues = [0, 1000000]
    public priceFilterSlideInitialMin = 0
    public priceFilterSlideInitialMax = 1000000
    public priceFilterPopoverEnable = false

    /**
     * vuex mappers and computed properties
     */

    @Mutation(Mutations.PRODUCT__UPDATE_FILTER_PRICE) updateProductFilterPrice
    @Mutation(Mutations.PRODUCT__RESET_FILTER_PRICE) resetProductFilterPrice

    @State(States.PRODUCT__FILTER_MIN_PRICE) filterMinPrice
    @State(States.PRODUCT__FILTER_MAX_PRICE) filterMaxPrice

    get isPriceFilterApplied() {
      return this.filterMinPrice !== this.priceFilterSlideInitialMin
    }

    /**
     * life-cycle methods
     */

    /**
     * event handlers
     */

    handlePriceSliderChange(value) {
    }

    handleFilterCancel() {
      this.resetSlideValues()
      this.resetProductFilterPrice()
      this.hidePriceFilterPopvoer()
    }

    handleFilterApply() {
      const minValue = this.priceFilterSliderValues[0]
      let maxValue = this.priceFilterSliderValues[1]

      if (maxValue === this.priceFilterSlideInitialMax) {
        maxValue = null
      }

      this.updateProductFilterPrice({minPrice: minValue, maxPrice: maxValue})
      this.hidePriceFilterPopvoer()
    }

    /**
     * helpers
     */

    hidePriceFilterPopvoer() {
      this.priceFilterPopoverEnable = false
    }

    resetSlideValues() {
      this.priceFilterSliderValues = [
        this.priceFilterSlideInitialMin,
        this.priceFilterSlideInitialMax
      ]
    }

    isMaxPriceSet() {
      const maxPrice = this.priceFilterSliderValues[1]
      return maxPrice == this.priceFilterSlideInitialMax
    }

    buildPriceRangeText() {
      const minPrice = this.priceFilterSliderValues[0]
      const maxPrice = this.priceFilterSliderValues[1]
      let text = `₩${minPrice} - ₩${maxPrice}`

      if (this.isMaxPriceSet()) {
        text += "+"
      }

      return text
    }
  }

</script>

<style scoped>

</style>
