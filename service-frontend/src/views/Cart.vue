<template>
  <div>
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">

        <!-- steps -->
        <div style="margin-top: 50px;">
          <el-steps :active="0" align-center
                    process-status="finish"
                    finish-status="success">
            <el-step title="Cart" description="Select items"></el-step>
            <el-step title="Order" description="Fill the form"></el-step>
            <el-step title="Payment" description="Checkout"></el-step>
          </el-steps>
        </div>

        <!-- cart items -->
        <div style="margin-top: 50px;">
          <el-table ref="cartItemTable"
                    :data="cartItemList"
                    :default-sort="{prop: 'price', order: 'descending'}"
                    :span-method="spanMethod"
                    @selection-change="handleCartItemSelectionChange"
                    style="width: 100%">

            <!-- table column: select checkbox -->
            <el-table-column type="selection" width="55">
            </el-table-column>

            <!-- table column: product image and description -->
            <el-table-column label="Product" width="400">
              <template slot-scope="scope">
                <div style="margin-left: 0;">
                  <el-row>
                    <el-col :xs="6" :sm="6" :md="6" :lg="6">
                      <div style="height: 80px; line-height: 80px; text-align: center; border: 2px dashed #f69c55;"
                           v-if="isProduct(scope.row)">
                        <span style="vertical-align: middle; line-height: normal;">
                          IMAGE
                        </span>
                      </div>
                    </el-col>
                    <el-col :xs="18" :sm="18" :md="18" :lg="18">
                      <!-- `Product` description -->
                      <div v-if="isProduct(scope.row)"
                           style="margin-left: 15px;">
                        <div style="">
                          <router-link :to="`/product/${scope.row.productId}`" class="cart-line-name">
                            {{ scope.row.name }}
                          </router-link>
                        </div>
                        <div style="margin-top: 2px;">
                          {{ scope.row.description }}
                        </div>
                      </div>
                      <!-- `Option` description -->
                      <div v-if="isOption(scope.row)"
                           style="margin-left: 0;">
                        <div style="font-weight: 700;">
                          {{ scope.row.name }}
                        </div>
                        <div style="margin-top: 2px;">
                          {{ scope.row.description }}
                        </div>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </template>
            </el-table-column>

            <!-- table column: price -->
            <el-table-column sortable label="Price" width="100">
              <template slot-scope="scope">
                <div>
                  <span style="">{{ scope.row.price }}</span>
                </div>
              </template>
            </el-table-column>

            <!-- table column: quantity -->
            <el-table-column label="Quantity" width="180">
              <template slot-scope="scope">
                <div v-if="isProduct(scope.row)">
                  <div style="text-align: center;">
                    <el-input-number size="mini"
                                     :min="0" :max="99"
                                     controls-position="right"
                                     @change="handleCartItemQuantityChange(scope.row)"
                                     v-model="scope.row.quantity"></el-input-number>
                  </div>
                  <div style="margin-top: 15px; text-align: center;">
                  <span class="productPriceSum">
                    {{ scope.row.quantity * (scope.row.price + scope.row.optionPriceSum) }}
                  </span>
                  </div>
                </div>
              </template>
            </el-table-column>

            <!-- table column: shipping information -->
            <el-table-column label="Shipping" width="180">
              <template slot-scope="scope">
                <div v-if="isProduct(scope.row)">
                  <div>
                    <el-tag type="info">
                      {{ scope.row.shippingFee === 0 ? 'Free': scope.row.shippingFee }}
                    </el-tag>
                  </div>
                  <div style="margin-top: 15px; margin-left: 1px;">
                    <i class="el-icon-date"></i>
                    <span>
                    {{ scope.row.expectedShippingDate }}
                  </span>
                  </div>
                </div>
              </template>
            </el-table-column>

            <!-- table column: operation -->
            <el-table-column fixed="right" width="140">
              <template slot-scope="scope">
                <div v-if="isProduct(scope.row)"
                     style="margin-left: 25px;">
                  <div style="">
                    <el-button size="small" type="primary" plain
                               style="width: 70px;"
                               @click="handleCartItemOrder(scope.$index, scope.row)">
                      Order
                    </el-button>
                  </div>
                  <div style=""
                       v-if="isProduct(scope.row)">
                    <el-button size="small" type="danger" plain
                               style="width: 70px; margin-top: 4px;"
                               @click="handleCartItemDelete(scope.$index, scope.row)">
                      Delete
                    </el-button>
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- cart action -->
        <div style="margin-top: 20px; margin-left: 14px;">
          <el-checkbox v-model="allChecked"
                       :disabled="!cartItemList || cartItemList.length === 0"
                       @change="handleCartAllItemToggle"></el-checkbox>
          <el-button type="warning" size="mini" plain
                     icon="el-icon-delete" style="margin-left: 20px;"
                     :disabled="!cartItemSelection || cartItemSelection.length === 0"
                     @click="handleSelectedCartItems">
            Deleted Selected (max: 100)
          </el-button>
        </div>

        <!-- cart summary -->
        <div style="margin-top: 40px; text-align: center;">
          <el-card style="width: 80%; margin: 0 auto;">
            <div>
              <!-- total price -->
              <span style="margin-left: 20px;">
              <el-tag class="priceTag">Total Price</el-tag>
            </span>
              <span class="price" style="margin-left: 10px;">
              {{ getProductPriceSum() }}
            </span>
              <!-- '+' -->
              <span style="margin-left: 10px;">
              <el-tag type="info" size="medium">
                <v-icon name="plus" style="vertical-align: middle"></v-icon>
              </el-tag>
            </span>
              <!-- shipping fee  -->
              <span style="margin-left: 20px;">
              <el-tag class="priceTag" type="primary">Shipping Fee</el-tag>
            </span>
              <span class="price" style="margin-left: 10px;">
              {{ getShippingPriceSum() }}
            </span>
              <!-- '=' -->
              <span style="margin-left: 10px;">
              <el-tag type="info">
                <v-icon name="equals" style="vertical-align: middle"></v-icon>
              </el-tag>
            </span>
              <!-- sum -->
              <span class="price totalPrice" style="margin-left: 20px;">
              {{ getTotalPrice() }}
            </span>
            </div>
          </el-card>
        </div>

        <!-- cart action -->
        <div style="margin-top: 30px; margin-bottom: 200px; text-align: center;">
        <span>
          <el-button type="primary" round>Proceed to checkout</el-button>
        </span>
        </div>

      </el-col>
    </el-row>

    <!-- page level component: back-to-top -->
    <back-to-top visibleoffset="300">
      <div class="backToTop">
        <i class="el-icon-caret-top"></i>
      </div>
    </back-to-top>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'
  import {CartAPI} from "../common/product.service"
  import {CartDTO, CartLineDTO, CartLineOptionDTO} from "../generated/swagger"
  import {handleFailure} from "../common/failure.util";

  const CartItemType = {
    Product: 'PRODUCT',
    OPTION: 'OPTION',
  }

  @Component
  export default class Cart extends Vue {
    public $refs: any
    public $notify: any
    public $router: any
    public $store: any

    public allChecked = false
    public cartItemSelection = []
    public cartItemList = []

    /**
     * life-cycle methods
     */

    mounted() {
      this.updateCartLines().catch(handleFailure)
    }

    updateCartLines(): Promise<CartDTO> {
      const fetched: Promise<CartDTO> =
        CartAPI.getUserCartLines({credentials: 'include'})

      // fetch and fill table data
      return fetched.then(cartDTO => {
        const cartLines = cartDTO.cartLines

        let rows = []

        for (let i = 0; i < cartLines.length; i++) {
          const cartLine = cartLines[i]
          const convertedRows = this.convertCartLine(cartLine)
          rows = rows.concat(convertedRows)
        }

        this.cartItemList = rows

        return cartDTO
      })
    }

    /**
     * event handlers
     */

    handleCartAllItemToggle(toggle) {
      if (toggle) {
        this.$refs.cartItemTable.toggleAllSelection()
      } else {
        this.$refs.cartItemTable.clearSelection()
      }
    }

    handleCartItemSelectionChange(changed) {
      this.cartItemSelection = changed

      if (changed.length === this.cartItemList.length) {
        this.allChecked = true
      } else if (changed.length === 0) {
        this.allChecked = false
      }
    }

    handleCartItemQuantityChange(row) {
      const cartLineId = row.cartLineId
      const quantity = row.quantity

      const request = {quantity: quantity,}

      // TODO: rollback quantity in frontend in case of failure
      CartAPI.updateUserCartLine(cartLineId, request, {credentials: 'include'})
        .then(response => {
          return this.updateCartLines()
        }).catch(handleFailure)
    }

    handleCartItemOrder(index, row) {
      console.log('ordered', row)
    }

    handleCartItemDelete(index, row) {
      const cartLineId = row.cartLineId
      CartAPI.removeUserCartLine(cartLineId, {credentials: 'include'})
        .then(response => {
          return this.updateCartLines()
        }).catch(handleFailure)
    }

    handleSelectedCartItems() {
      const cartLineIdList = []

      for (let i = 0; i < this.cartItemSelection.length; i++) {
        const row = this.cartItemSelection[i]

        // skip if it's product. (row could be an option.)
        if (!this.isProduct(row)) {
          continue
        }

        cartLineIdList.push(row.cartLineId)
      }

      cartLineIdList.sort() // for sequential disk access in server side :)
      const query = cartLineIdList.toString();

      CartAPI.removeUserCartLines(query, {credentials: 'include'})
        .then(response => {
          return this.updateCartLines()
        }).catch(handleFailure)
    }

    spanMethod({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 0 || columnIndex === 5) {
        if (this.isOption(row)) {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }

      if (columnIndex === 0 || columnIndex === 3 || columnIndex === 4 || columnIndex === 5) {
        let nextRowIndex = rowIndex + 1
        let hasNext = nextRowIndex < this.cartItemList.length
        let hasNextOption = hasNext && this.isOption(this.cartItemList[nextRowIndex])

        let rowspan = 1
        while (hasNextOption) {
          nextRowIndex += 1
          hasNext = nextRowIndex < this.cartItemList.length
          hasNextOption = hasNext && this.isOption(this.cartItemList[nextRowIndex])
          rowspan += 1
        }

        return {
          rowspan: rowspan,
          colspan: 1,
        }
      }
    }

    /**
     * helpers
     */

    isProduct(row) {
      return row.type === CartItemType.Product
    }

    isOption(row) {
      return row.type === CartItemType.OPTION
    }

    getProductPriceSum() {
      const totalPrice = this.cartItemList.reduce((total, r) => {
        if (this.isProduct(r)) {
          return total + ((r.price + r.optionPriceSum) * r.quantity)
        } else {
          return total
        }
      }, 0)

      return totalPrice
    }

    getShippingPriceSum() {
      const totalShippingFee = this.cartItemList.reduce((total, r) => {
        if (this.isProduct(r)) {
          return total + r.shippingFee
        } else {
          return total
        }
      }, 0)
      return totalShippingFee
    }

    getTotalPrice() {
      return this.getProductPriceSum() + this.getShippingPriceSum()
    }

    convertCartLine(cartLine: CartLineDTO): Array<any> {
      let rows = []

      // add product row
      const productRow = this.convertCartLineToRow(cartLine)
      rows.push(productRow)

      // add option rows
      if (cartLine.cartLineOptions.length > 0) {
        const optionsRows = cartLine
          .cartLineOptions
          .map(o => this.convertCartLineOptionToRow(o))

        rows = rows.concat(optionsRows)
      }

      return rows
    }

    convertCartLineToRow(cartLine: CartLineDTO): any {
      const optionPriceSum = cartLine
        .cartLineOptions
        .reduce((total, o) => total + o.productOptionPrice, 0)

      const expectedShippingDate = '2018. 10. 24'
      const shippingFee = 0

      return {
        type: CartItemType.Product,
        cartLineId: cartLine.cartLineId,
        quantity: cartLine.quantity,
        productId: cartLine.productId,
        name: cartLine.productName,
        description: cartLine.productDescription,
        price: cartLine.productPrice,
        optionPriceSum: optionPriceSum,
        shippingFee: shippingFee,
        expectedShippingDate: expectedShippingDate,
      }
    }

    convertCartLineOptionToRow(cartLineOption: CartLineOptionDTO): any {
      return {
        type: CartItemType.OPTION,
        cartLineOptionId: cartLineOption.cartLineOptionId,
        quantity: cartLineOption.quantity,
        price: cartLineOption.productOptionPrice,
        name: cartLineOption.productOptionName,
        description: cartLineOption.productOptionDescription,
        productOptionId: cartLineOption.productOptionId,
      }
    }
  }
</script>

<style lang="scss" scoped>
  .priceTag {
    font-size: 14px;
  }

  .price {
    font-size: 18px;
    vertical-align: middle;
  }

  .totalPrice {
    font-weight: bold;
  }

  .productPriceSum {
    font-size: 17px;
    color: #303133;
  }

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

  .cart-line-name {
    text-decoration: none;
    font-weight: 700;
    color: #606266;
  }

  .cart-line-name:hover {
    text-decoration: underline;
    cursor: pointer;
  }
</style>