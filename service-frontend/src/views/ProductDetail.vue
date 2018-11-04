<template>
  <div>
    <!-- category display -->
    <el-row type="flex" justify="center" class="category-container">
      <el-col :xs="20" :sm="20" :md="20" :lg="20">
         <span style="margin-left: 8px; margin-right: 8px;">
           <router-link :to="{name: 'product'}">
             <el-button icon="el-icon-menu" :to="'/product'">
               Products
             </el-button>
           </router-link>
         </span>
        <template v-for="(category, index) in computedCurrentCategories">
           <span style="margin-left: 8px; margin-right: 8px;">
             <i class="el-icon-arrow-right"></i>
           </span>
          <el-select v-model="computedCurrentCategories[index]"
                     placeholder="Select">
            <el-option
              :key="computedOptionsPerCategory[index][0].value"
              :label="computedOptionsPerCategory[index][0].label"
              :value="computedOptionsPerCategory[index][0].value">
            </el-option>
          </el-select>
        </template>
      </el-col>
    </el-row>

    <!-- product title, description, review rate -->
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="20" :lg="20">
        <div class="title-container">
          <span class="title-name">{{ stateProductItem.name }}</span>
          <el-rate v-model="reviewRate" class="title-review-rank"></el-rate>
          <p class="title-description">
            {{ stateProductItem.description }}
          </p>
        </div>
      </el-col>
    </el-row>

    <!-- product image, option transfer and operations -->
    <el-row class="detail-container">
      <el-row type="flex" justify="center">
        <el-col :xs="20" :sm="20" :md="20" :lg="20">
          <el-row>

            <!-- image -->
            <el-col :xs="20" :sm="20" :md="10" :lg="10">
              <div class="image-container">
                <el-card :body-style="{ padding: '0px' }">
                  <el-carousel trigger="click" height="350px" :autoplay="false">
                    <el-carousel-item>
                      <img src="../assets/gopher2.jpg" width="100%" height="100%"/>
                    </el-carousel-item>
                    <el-carousel-item>
                      <img src="../assets/gopher1.png" width="100%" height="100%"/>
                    </el-carousel-item>
                  </el-carousel>
                </el-card>
              </div>
            </el-col>

            <!-- option selectbox + operations -->
            <el-col :xs="20" :sm="20" :md="14" :lg="14" v-if="stateProductItem">
              <div class="price-and-option-container">
                <div class="price-container">
                  <p class="total-price-text"> Price: {{ totalPrice }} KRW </p>
                </div>

                <!-- operation selectbox -->
                <div class="option-container">
                  <template>
                    <el-transfer
                      @change="handleOptionTransferSelectedItemChange"
                      @left-check-change="handleOptionTransferLeftChange"
                      @right-check-change="handleOptionTransferRightChange"
                      :titles="['Options', 'Selected']"
                      v-model="selectedOptionIndexs"
                      :data="computedAvailableOptionIndices">
                    </el-transfer>
                  </template>
                </div>

                <!-- operations: quantity, add to cart, order -->
                <div class="button-container">
                  <el-input-number class="input-quantity" @change="handleQuantityChange"
                                   :min="1" :max="99" v-model="quantity" size="meidum"
                                   controls-position="right"></el-input-number>

                  <el-popover placement="top-end" width="400"
                              trigger="manual" v-model="cartItemAddedPopup">
                    <p>Successfully added this item into the cart.</p>
                    <div style="text-align: right; margin: 0">
                      <el-button type="primary" size="small" @click="handleGoToCartButtonClick">View Cart</el-button>
                      <el-button type="text" size="small" @click="handleCartItemAddedPopupButtonClick">Close</el-button>
                    </div>
                    <el-button slot="reference"
                               class="button-add-to-cart"
                               v-on:click="handleAddToCartClick">
                    <span style="vertical-align: middle;">
                      <v-icon name="shopping-cart"></v-icon>
                    </span>
                      <span style="margin-left: 4px;">
                      Add to Cart
                    </span>
                    </el-button>
                  </el-popover>

                  <el-button type="primary" class="button-buy-now">Buy now</el-button>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-row>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import * as States from "@/store/state_type"
  import * as Mutations from "@/store/mutation_type"
  import * as Actions from "@/store/action_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"

  @Component({
    components: {},
    props: {},
  })
  export default class ProductDetail extends Vue {
    public $refs: any
    public $router: any
    public $route: any

    public cartItemAddedPopup = false
    public productId: number = 0
    public quantity: number = 1
    public totalPrice: number = 0
    public selectedOptionIndexs = []
    public reviewRate: number = 5 // TODO

    /**
     * vuex mappers and computed properties
     */

    @State(States.DETAIL__PRODUCT_ITEM) stateProductItem
    @State(States.DETAIL__PRODUCT_OPTION_LIST) stateProductOptionList
    @State(States.DETAIL__PRODUCT_QUANTITY) stateProductQuantity

    @Mutation(Mutations.DETAIL__SET_QUANTITY) commitProductQuantity

    @Action(Actions.DETAIL__FETCH_PRODUCT_ITEM) actionFetchDetailItem
    @Action(Actions.DETAIL__ADD_TO_CART) actionAddToCart

    get computedAvailableOptionIndices() {
      const availableOptionIndices = this.stateProductOptionList.map((option, index) => {
        return {
          key: index,
          label: `${option.name} (${option.price})`,
          disabled: false,
        }
      })

      return availableOptionIndices
    }

    get computedCurrentCategories() {
      if (!this.stateProductItem.categoryPath) {
        return []
      }

      const filtered = this.stateProductItem.categoryPath
        .split("/")
        .filter(x => x.trim() !== "")

      return filtered
    }

    get computedOptionsPerCategory() {
      if (!this.stateProductItem.categoryPath) {
        return []
      }

      const filtered = this.stateProductItem.categoryPath
        .split("/")
        .filter(x => x.trim() !== "")

      const optionsPerCategory = filtered.map(x => [{label: x, value: x}])

      return optionsPerCategory
    }

    /**
     * life-cycle methods
     */

    created() {
      const productId = this.$route.params.productID
      this.productId = productId
      this.actionFetchDetailItem(productId)
        .then(() => {
          this.calculatePrice()
        })

    }

    /**
     * event handlers
     */

    handleAddToCartClick() {
      const selectedOptionIdList = this.selectedOptionIndexs.map(optionIndex => {
        return this.stateProductOptionList[optionIndex].id
      })

      this.addProductToCart(this.productId, selectedOptionIdList)
    }

    handleOptionTransferSelectedItemChange() {
      this.calculatePrice()
      this.closeCartItemAddedPopup()
    }

    handleQuantityChange(quantity) {
      this.commitProductQuantity(quantity)
      this.calculatePrice()
    }

    handleGoToCartButtonClick() {
      this.$router.push({
        name: "cart",
        params: {}
      })
    }

    handleCartItemAddedPopupButtonClick() {
      this.closeCartItemAddedPopup()
    }

    handleOptionTransferLeftChange() {
      this.closeCartItemAddedPopup()
    }

    handleOptionTransferRightChange() {
      this.closeCartItemAddedPopup()
    }

    /**
     * helpers
     */

    addProductToCart(productId, selectedOptionIdList) {
      this.actionAddToCart({
        productId: productId,
        productOptionIdList: selectedOptionIdList,
      }).then(() => {
        this.openCartItemAddedPopup()
      })
    }

    closeCartItemAddedPopup() {
      this.cartItemAddedPopup = false
    }

    openCartItemAddedPopup() {
      this.cartItemAddedPopup = true
    }

    calculatePrice() {
      const selectedOptionIndexList = this.selectedOptionIndexs
      let pricePerItem = this.stateProductItem.price

      if (!selectedOptionIndexList || selectedOptionIndexList.length === 0) {
        this.totalPrice = pricePerItem
      }

      selectedOptionIndexList.map(optionIndex => {
        const optionPrice = this.stateProductOptionList[optionIndex].price
        pricePerItem += optionPrice
      })
      this.totalPrice = pricePerItem * this.stateProductQuantity
    }
  }
</script>

<style scoped>
  .detail-container {
    margin-top: 15px;
  }

  .image-container {
    padding-left: 15px;
    padding-right: 15px;
  }

  .category-container {
    margin-top: 20px;
  }

  .title-container {
    margin-top: 40px;
    margin-bottom: 35px;
    padding-left: 15px;
    padding-right: 15px;
  }

  .title-name {
    font-weight: 700;
    font-size: 30px;
  }

  .title-review-rank {
    display: inline-block;
    margin-left: 13px;
    margin-top: 4px;
    vertical-align: top;
  }

  .el-icon-star-on {
    font-size: 30px;
    margin-bottom: 5px;
  }

  .el-icon-star-off {
    font-size: 30px;
    margin-bottom: 5px;
  }

  .title-description {
    font-size: 20px;
    margin-top: 10px;
    margin-left: 10px;
    margin-bottom: 10px;
  }

  .price-and-option-container {
    margin-left: 40px;
  }

  .total-price-text {
    margin-top: 0px;
    margin-bottom: 25px;
    font-size: 25px;
  }

  .option-container {
    margin-top: 30px;
  }

  .button-container {
    margin-top: 30px;
  }

  .input-quantity {
    margin-right: 15px;
  }

  .button-buy-now {
    width: 145px;
    margin-right: 5px;
  }

  .button-add-to-cart {
    width: 145px;
    margin-right: 5px;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>