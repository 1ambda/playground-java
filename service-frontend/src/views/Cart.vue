<template>
  <el-row type="flex" justify="center">
    <el-col :xs="20" :sm="20" :md="18" :lg="18">

      <!-- cart items -->
      <div>
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
          <el-table-column label="Product" width="350">
            <template slot-scope="scope">
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
                  <div style="margin-left: 10px; font-weight: 700;">
                    {{ scope.row.name }}
                  </div>
                  <div style="margin-left: 10px; margin-top: 2px;">
                    {{ scope.row.description }}
                  </div>
                </el-col>
              </el-row>
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
          <el-table-column label="Shipping" width="220">
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
          <el-table-column fixed="right" width="160">
            <template slot-scope="scope">
              <div v-if="isProduct(scope.row)">
                <div style="margin-left: 15px;">
                  <el-button size="small" type="primary" plain
                             style="width: 70px;"
                             @click="handleEdit(scope.$index, scope.row)">
                    Order
                  </el-button>
                </div>
                <div style="margin-left: 15px;"
                     v-if="isProduct(scope.row)">
                  <el-button size="small" type="danger" plain
                             style="width: 70px; margin-top: 4px;"
                             @click="handleDelete(scope.$index, scope.row)">
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
                     @change="handleCartAllItemToggle"></el-checkbox>
        <el-button type="warning" size="mini"
                   plain
                   icon="el-icon-delete"
                   style="margin-left: 20px;">
          Deleted Selected
        </el-button>
      </div>

      <!-- cart summary -->
      <div style="margin-top: 40px; text-align: center;">
        <el-card style="width: 80%; margin: 0 auto;" >
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
</template>

<script>
  export default {
    data() {
      return {
        allChecked: false,
        cartItemSelection: [],
        cartItemList: [
          {
            type: 'PRODUCT',
            name: 'LG Notebook GRAM 13',
            description: 'LG gram Thin & Light Laptop - Up to 16.5 hrs, Thunderbolt 3, Finger Print Reader (Windows not installed)',
            quantity: 1,
            price: 1350000,
            optionPriceSum: 260000,
            shippingFee: 0,
            expectedShippingDate: '2018. 10. 24',
          },
          {
            type: 'OPTION',
            name: 'Memory 8 GB+',
            description: '',
            quantity: 1,
            price: 160000,
          },
          {
            type: 'OPTION',
            name: 'SSD 256 GB+',
            description: '',
            quantity: 1,
            price: 160000,
          },
          {
            type: 'PRODUCT',
            name: 'Samsung 7 Pro',
            description: 'SSD 128 GB',
            quantity: 2,
            price: 1359000,
            optionPriceSum: 75000,
            shippingFee: 2500,
            expectedShippingDate: '2018. 11. 02',
          },
          {
            type: 'OPTION',
            name: 'OS Installation',
            description: 'Windows 10 Home',
            quantity: 1,
            price: 75000,
          },
          {
            type: 'PRODUCT',
            name: 'AMD Ryzen 7 2700X',
            description: '',
            quantity: 1,
            price: 420000,
            optionPriceSum: 0,
            shippingFee: 0,
            expectedShippingDate: '2018. 10. 28',
          },
          {
            type: 'PRODUCT',
            name: 'AMD Ryzen 5 2600',
            description: '',
            quantity: 1,
            price: 234000,
            optionPriceSum: 0,
            shippingFee: 0,
            expectedShippingDate: '2018. 11. 10',
          },
        ],
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },

      handleCartAllItemToggle(toggle) {
        if (toggle) {
          for(let i = 0; i < this.cartItemList.length; i++) {
            const row = this.cartItemList[i]
            this.$refs.cartItemTable.toggleRowSelection(row, true)
          }
        } else {
          this.$refs.cartItemTable.clearSelection();
        }
      },

      handleCartItemSelectionChange(changed) {
        this.cartItemSelection = changed;

        if (changed.length === this.cartItemList.length) {
          this.allChecked = true
        } else if (changed.length === 0) {
          this.allChecked = false;
        }
      },

      spanMethod2({row, column, rowIndex, columnIndex}) {
        const nextRowIndex = rowIndex + 1
        const hasNext = nextRowIndex < this.$data.cartItemList.length

        if (columnIndex === 0) {
          if (this.isOption(row)) {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }

        if (columnIndex === 0 || columnIndex === 3 || columnIndex === 4 || columnIndex === 5) {

          if (hasNext && this.isOption(this.$data.cartItemList[nextRowIndex])) {
            return {
              rowspan: 2,
              colspan: 1
            };
          } else {
            return {
              rowspan: 1,
              colspan: 1
            };
          }
        }
      },

      spanMethod({row, column, rowIndex, columnIndex}) {

        if (columnIndex === 0 || columnIndex === 5) {
          if (this.isOption(row)) {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }

        if (columnIndex === 0 || columnIndex === 3 || columnIndex === 4 || columnIndex === 5) {
          let nextRowIndex = rowIndex + 1
          let hasNext = nextRowIndex < this.$data.cartItemList.length
          let hasNextOption = hasNext && this.isOption(this.$data.cartItemList[nextRowIndex])

          let rowspan= 1;
          while(hasNextOption) {
            nextRowIndex += 1
            hasNext = nextRowIndex < this.$data.cartItemList.length
            hasNextOption = hasNext && this.isOption(this.$data.cartItemList[nextRowIndex])
            rowspan +=1;
          }

          return {
            rowspan: rowspan,
            colspan: 1,
          }
        }
      },

      isProduct(row) {
        return row.type === 'PRODUCT';
      },

      isOption(row) {
        return row.type === 'OPTION';
      },

      getProductPriceSum() {
        const totalPrice = this.$data.cartItemList
          .reduce((total, r) => {
            if (this.isProduct(r)) {
              return total + ((r.price + r.optionPriceSum) * r.quantity)
            } else {
              return total
            }
          }, 0)

        return totalPrice;
      },
      getShippingPriceSum() {
        const totalShippingFee = this.$data.cartItemList
          .reduce((total, r) => {
            if (this.isProduct(r)) {
              return total + r.shippingFee
            } else {
              return total
            }
          }, 0)
        return totalShippingFee
      },
      getTotalPrice() {
        return this.getProductPriceSum() + this.getShippingPriceSum();
      }
    }
  }
</script>
<style>
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

</style>