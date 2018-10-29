<template>
  <div>

    <!-- search inputbox -->
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 25px; width: 800px;">
          <el-input placeholder="Please input"
                    class="product-searchbox-input"
                    v-model="searchInsertedKeyword">
            <el-select slot="prepend" placeholder="Select"
                       class="product-searchbox-select"
                       v-model="searchSelectedCategory">
              <el-option v-for="(item, index) in searchAvailableCategoryList"
                         :key="index"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
      </el-col>
    </el-row>

    <!-- filters -->
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div class="product-filter-section" style="padding-top: 16px; padding-bottom: 16px; margin-top: 20px;">
          <span>
            <el-button type="primary" plain>Price</el-button>
          </span>
          <span style="margin-left: 10px;">
            <el-button type="primary" plain>Tag</el-button>
          </span>
          <span style="margin-left: 10px;">
            <el-button type="primary" plain>Rate</el-button>
          </span>
          <span style="margin-left: 10px;">
            <el-button type="primary" plain>Shipping</el-button>
          </span>

          <span style="float: right; vertical-align: middle; padding-top: 11px; margin-right: 5px;">
            <el-switch
              v-model="toggleGridViewType"
              disabled
              active-text="Grid View"
              inactive-color="#ff4949"
              inactive-text="">
            </el-switch>
          </span>

          <span style="float: right; vertical-align: middle; padding-top: 7px; margin-right: 15px; width: 70px;">
              <el-select v-model="paginationSize" placeholder="Select" size="mini">
                <el-option v-for="(item, index) in availablePaginationSizeList"
                           :key="index"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
          </span>
          <span style="float:right; vertical-align: middle; padding-top: 11px; margin-right: 10px;">
            Results per page
          </span>

        </div>
      </el-col>
    </el-row>

    <!-- search summary -->
    <el-row type="flex" justify="center" v-if="toggleGridViewType">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 30px; margin-left: 20px;">
          <span class="search-summary-text search-summary-pagination">
            1-{{ paginationSize }} of
          </span>
          <span class="search-summary-text search-summary-total-count">
             36
          </span>
          <span class="search-summary-text">
             results for
          </span>
          <span style="padding-top: 2px;">
            <el-tag type="warning" size="medium">Laptop</el-tag>
          </span>
        </div>
      </el-col>
    </el-row>

    <!-- product grid -->
    <el-row type="flex" justify="center" v-if="toggleGridViewType">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 50px;">
          <ProductGrid></ProductGrid>
        </div>
      </el-col>
    </el-row>

    <!-- product list (disabled) -->
    <el-row type="flex" justify="center" v-if="!toggleGridViewType">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 40px;">
          <el-table :data="productList"
                    stripe border
                    @cell-click='handleCellClick'
                    style="width: 100%">

            <el-table-column v-for="column in columns"
                             :class-name="column.prop === 'name' ? 'column-name' : 'column-default'"
                             :prop="column.prop" :label="column.label"
                             :key="column.label">
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>

    <!-- pagination -->
    <el-row type="flex" justify="center"
            style="margin-top: 40px; text-align: center;">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <el-pagination background
                       layout="prev, pager, next"
                       :page-size="itemCountPerPage"
                       :current-page="currentPage"
                       @current-change="handleCurrentPageChange"
                       :total="totalItemCount">
        </el-pagination>
      </el-col>
    </el-row>

  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import {Pagination} from "@/generated/swagger"
  import {CatalogAPI,} from "@/common/product.service.ts"
  import {handleFailure} from "@/common/failure.util"
  import ProductGrid from "@/views/ProductGrid.vue"
  import {Action, Getter, Mutation, State,} from "vuex-class"

  @Component({
    components: {ProductGrid},
  })
  export default class Product extends Vue {
    public $refs: any
    public $notify: any
    public $router: any
    public $store: any

    @Mutation(Mutations.PRODUCT__UPDATE_ITEMS) updateProductListStore
    @Mutation(Mutations.PRODUCT__UPDATE_TOTAL_COUNT) updateProductTotalCount
    @Mutation(Mutations.PRODUCT__UPDATE_CURRENT_PAGE) updateProductCurrentPage
    @State(States.PRODUCT__FETCHED_ITEMS) products
    @State(States.PRODUCT__TOTAL_COUNT) totalItemCount
    @State(States.PRODUCT__CURRENT_PAGE) currentPage // offset + 1

    public itemCountPerPage = 8
    public columns = [
      {prop: "name", label: "Name",},
      {prop: "categoryDisplayName", label: "Category",},
      {prop: "description", label: "Description",},
      {prop: "onSale", label: "On Sale",},
      {prop: "price", label: "Price (KRW)",},
      {prop: "createdAt", label: "Registered At",},
    ]
    public productList = []

    public paginationSize = "8"
    public availablePaginationSizeList = [
      {value: "8", label: "8"},
      {value: "16", label: "16"},
      {value: "32", label: "32"},
      {value: "48", label: "48"},
    ]

    public searchSelectedCategory = "category1"
    public searchAvailableCategoryList = [
      {value: "category1", label: "Category 1"},
      {value: "category2", label: "Category 2"},
      {value: "category3", label: "Category 3"},
    ]
    public searchInsertedKeyword = ""
    public toggleGridViewType = true

    /**
     * life-cycle methods
     */

    mounted() {
      this.fetchAllProducts(this.currentPage)
    }

    /**
     * event handlers
     */

    /**
     * helpers
     */

    fetchAllProducts(currentPage) {
      const page = currentPage - 1
      const size = this.itemCountPerPage
      const sort = []
      const options = {credentials: "include"}

      const categoryId = null
      const search = ""

      const minPrice = 0
      const maxPrice = 300000
      const minRate = null
      const tags = []
      const minShippingDate = null

      CatalogAPI.findPaginatedProducts(
        page, size, sort,
        categoryId, search,
        minPrice, maxPrice, minRate, tags, minShippingDate, options)
        .then((response: any) => {
          const pagination: Pagination = response.pagination

          const totalItemCount = pagination.totalItemCount
          this.updateProductTotalCount(totalItemCount)

          const products = response.products.map(p => p.item)
          this.updateProductListStore(products)

        }).catch(handleFailure(this.$notify))
    }

    handleCurrentPageChange(newPage) {
      this.updateProductCurrentPage(newPage)
      this.fetchAllProducts(newPage)
    }

    handleCellClick(row, column, cell, event) {
      const productID = row.id
      const columnName = column.property
      if (columnName === "name") {
        this.$router.push({
          name: "product.detail",
          params: {
            productID: productID,
          }
        })
      }
    }
  }
</script>

<!-- can't set element-ui inner class without global scope -->
<style lang="scss">
  .column-name:hover {
    text-decoration: underline;
    cursor: pointer;
  }

  .column-default {
  }

  .product-filter-section {
    border-top-color: #E4E7ED;
    border-top-style: solid;
    border-top-width: 1px;
    border-bottom-color: #E4E7ED;
    border-bottom-style: solid;
    border-bottom-width: 1px;
  }

  .product-searchbox-input .el-input {
    width: 130px;
  }

  .product-searchbox-input .el-input-group__prepend {
    background-color: #fff;
  }

  .product-searchbox-input {
    background-color: #fff;
    height: 54px;
  }

  .product-searchbox-input .el-input__inner {
    height: 56px;
  }

  .search-summary-text {
    font-size: 17px;
    font-weight: 300;
  }

  .search-summary-total-count {
    font-size: 17px;
    font-weight: 300;
  }

</style>
