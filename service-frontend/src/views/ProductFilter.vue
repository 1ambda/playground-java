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
          <el-button type="text" size="mini" @click="handlePriceFilterCancel">Cancel</el-button>
          <el-button type="primary" size="mini" @click="handlePriceFilterApply">Apply</el-button>
        </div>
      </div>

      <!-- price filter button -->
      <el-button v-if="!isPriceFilterApplied" slot="reference" type="primary" plain>
        Price
      </el-button>
      <el-button v-if="isPriceFilterApplied" slot="reference" type="primary">
        <span>{{ buildAppliedPriceRangeText() }}</span>
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
  import * as Actions from "@/store/action_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  // Register the router hooks with their names

  @Component({
    name: "ProductFilter",
  })
  export default class ProductFilter extends Vue {
    public $refs: any
    public $notify: any
    public $router: any
    public $route: any

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

    @Action(Actions.PRODUCT__FETCH_PAGINATED_ITEMS) actionFetchPaginatedItems

    get isPriceFilterApplied() {
      return this.filterMinPrice !== this.priceFilterSlideInitialMin ||
        (this.filterMaxPrice !== null &&
          this.filterMaxPrice !== this.priceFilterSlideInitialMax)
    }

    /**
     * life-cycle methods
     */

    created() {
      // apply filter queries into state
      // this should be done before drawing components (`created`)
      this.initializePriceFilter()
    }

    mounted() {

    }

    initializePriceFilter() {
      let minPrice = this.$route.query.minPrice
      let maxPrice = this.$route.query.maxPrice

      if (minPrice && Number(minPrice)) {
        minPrice = Number(minPrice)

        if (minPrice >= 0) {
          this.priceFilterSliderValues[0] = minPrice
        }
      }

      if (maxPrice && Number(maxPrice)) {
        maxPrice = Number(maxPrice)

        if (maxPrice >= 0) {

          if (minPrice && maxPrice > minPrice) {
            this.priceFilterSliderValues[1] = maxPrice
          } else if (!minPrice) {
            this.priceFilterSliderValues[1] = maxPrice
          }
        }
      }

      this.setPriceFilterState(minPrice, maxPrice)

    }

    /**
     * event handlers
     */

    handlePriceSliderChange(value) {
    }

    handlePriceFilterCancel() {
      this.resetSlideValues()
      this.resetProductFilterPrice()
      this.hidePriceFilterPopvoer()

      this.actionFetchPaginatedItems()
      this.setRouterQueryForPrice()
    }

    handlePriceFilterApply() {
      const minValue = this.priceFilterSliderValues[0]
      let maxValue = this.priceFilterSliderValues[1]

      this.setPriceFilterState(minValue, maxValue)
      this.hidePriceFilterPopvoer()

      this.actionFetchPaginatedItems()
      this.setRouterQueryForPrice()
    }

    /**
     * helpers
     */

    setPriceFilterState(minValue, maxValue) {
      if (!minValue) {
        minValue = 0
      }
      if (!maxValue || maxValue === this.priceFilterSlideInitialMax) {
        maxValue = null
      }

      this.updateProductFilterPrice({minPrice: minValue, maxPrice: maxValue})
    }

    setRouterQueryForPrice() {
      const minValue = this.priceFilterSliderValues[0]
      let maxValue = this.priceFilterSliderValues[1]

      // build query
      let query = Object.assign({}, this.$route.query)

      if (minValue !== this.priceFilterSlideInitialMin) {
        query["minPrice"] = minValue
      } else {
        delete query["minPrice"]
      }

      if (maxValue !== this.priceFilterSlideInitialMax) {
        query ["maxPrice"] = maxValue
      } else {
        delete query["maxPrice"]
      }

      this.$router.push({query: query,})
    }

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

    buildAppliedPriceRangeText() {
      const minPrice = this.filterMinPrice
      let maxPrice = this.filterMaxPrice

      if (maxPrice == null) {
        maxPrice = this.priceFilterSlideInitialMax
      }

      let text = `₩${minPrice} - ₩${maxPrice}`

      if (maxPrice === this.priceFilterSlideInitialMax) {
        text += "+"
      }

      return text
    }
  }

</script>

<style scoped>

</style>
