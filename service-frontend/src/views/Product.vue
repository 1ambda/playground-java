<template>
  <div>

    <!-- search inputbox -->
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 25px; width: 800px;">
          <el-input placeholder="Please input"
                    class="product-searchbox-input"
                    v-model="searchInsertedKeyword"
                    @keyup.enter.native="handleSearchKeywordEnter">
            <el-select slot="prepend" placeholder="Select"
                       class="product-searchbox-select"
                       v-model="searchSelectedCategory"
                       @change="handleSearchCategoryChange">
              <el-option v-for="(item, index) in stateSearchAvailableCategories"
                         :key="index"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search"
                       @click="handleSearchKeywordEnter"></el-button>
          </el-input>
        </div>
      </el-col>
    </el-row>

    <!-- filters -->
    <el-row type="flex" justify="center">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div class="product-filter-section" style="padding-top: 16px; padding-bottom: 16px; margin-top: 20px;">
          <ProductFilter></ProductFilter>
        </div>
      </el-col>
    </el-row>

    <!-- search summary -->
    <el-row type="flex" justify="center" v-if="toggleGridViewType">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <div style="margin-top: 30px; margin-left: 20px;">
          <span class="search-summary-text search-summary-pagination">
            1-{{ stateProducts.length }} of
          </span>
          <span class="search-summary-text search-summary-total-count">
             {{ stateTotalItemCount }}
          </span>
          <span class="search-summary-text">
             results for
          </span>
          <span style="padding-top: 2px;">
            <el-tag type="warning" size="medium">{{ displayNameForSelectedCategory }}</el-tag>
          </span>

          <!-- grid view toggle -->
          <span style="float: right; vertical-align: middle; padding-top: 4px; margin-right: 5px;">
            <el-switch
              v-model="toggleGridViewType"
              disabled
              active-text="Grid View"
              inactive-color="#ff4949"
              inactive-text="">
            </el-switch>
          </span>

          <!-- pagination count control -->
          <span style="float: right; vertical-align: middle; margin-right: 15px; width: 70px;">
              <el-select placeholder="Select" size="mini"
                         :value="stateItemCountPerPage" @change="handlePaginationSizeChange">
                <el-option v-for="(item, index) in availablePaginationSizeList"
                           :key="index"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
          </span>

          <span style="float:right; vertical-align: middle; padding-top: 4px; margin-right: 10px;">
            Results per page
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

    <!-- pagination -->
    <el-row type="flex" justify="center"
            style="margin-top: 40px; text-align: center;">
      <el-col :xs="20" :sm="20" :md="18" :lg="18">
        <el-pagination ref="pagination"
                       background layout="prev, pager, next"
                       :page-size="stateItemCountPerPage"
                       :current-page="stateCurrentPage"
                       :total="stateTotalItemCount"
                       @current-change="handleCurrentPageChange">
        </el-pagination>
      </el-col>
    </el-row>

  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"
  import * as Mutations from "@/store/mutation_type"
  import * as States from "@/store/state_type"
  import * as Actions from "@/store/action_type"
  import ProductGrid from "@/views/ProductGrid.vue"
  import ProductFilter from "@/views/ProductFilter.vue"
  import {Action, Getter, Mutation, State,} from "vuex-class"

  @Component({
    components: {ProductGrid, ProductFilter},
  })
  export default class Product extends Vue {
    public $refs: any
    public $notify: any
    public $route: any
    public $router: any
    public $store: any

    public availablePaginationSizeList = [
      {value: 4, label: "4"},
      {value: 8, label: "8"},
      {value: 16, label: "16"},
      {value: 32, label: "32"},
      {value: 48, label: "48"},
    ]

    public searchSelectedCategory = "all"
    public searchInsertedKeyword = ""
    public toggleGridViewType = true


    /**
     * vuex mappers and computed properties
     */

    @Mutation(Mutations.PRODUCT__UPDATE_ITEMS) commitUpdateItems
    @Mutation(Mutations.PRODUCT__UPDATE_FILTER_PRICE) updateProductFilterPrice
    @Mutation(Mutations.PRODUCT__RESET_FILTER_PRICE) resetProductFilterPrice

    @Mutation(Mutations.PRODUCT__UPDATE_ITEM_COUNT_PER_PAGE) commitSetItemCountPerPage
    @Mutation(Mutations.PRODUCT__UPDATE_TOTAL_ITEM_COUNT) commitUpdateTotalCount
    @Mutation(Mutations.PRODUCT__UPDATE_CURRENT_PAGE) commitUpdateCurrentPage

    @Mutation(Mutations.PRODUCT__SEARCH_UPDATE_KEYWORD) commitUpdateSearchKeyword
    @Mutation(Mutations.PRODUCT__SEARCH_UPDATE_CATEGORY) commitUpdateSearchCategory

    @State(States.PRODUCT__FETCHED_ITEMS) stateProducts
    @State(States.PRODUCT__TOTAL_COUNT) stateTotalItemCount
    @State(States.PRODUCT__CURRENT_SIZE) stateItemCountPerPage
    @State(States.PRODUCT__CURRENT_PAGE) stateCurrentPage

    @State(States.PRODUCT__SEARCH_KEYWORD) stateSearchKeyword
    @State(States.PRODUCT__SEARCH_CATEGORY) stateSearchCategory
    @State(States.PRODUCT__SEARCH_AVAILABLE_CATEGORIES) stateSearchAvailableCategories

    @Action(Actions.PRODUCT__FETCH_PAGINATED_ITEMS) actionFetchPaginatedItems
    @Action(Actions.PRODUCT__FETCH_CATEGORY_LIST) actionFetchCategories

    get displayNameForSelectedCategory() {
      const selected = this.stateSearchCategory

      if (selected === "all") {
        return this.stateSearchAvailableCategories[0].label
      }

      const index = this.stateSearchAvailableCategories.findIndex(category => {
        return category.value === selected
      })

      if (index === -1) {
        return ""
      }

      return this.stateSearchAvailableCategories[index].label
    }

    /**
     * life-cycle methods
     */

    created() {
    }

    mounted() {
      this.actionFetchCategories()
        .then(() => {
          this.commitPaginationFromQuery()
          this.commitSearchFromQuery()
          this.actionFetchPaginatedItems()
        })
    }

    public adjustPageSize(query) {
      if (query < this.availablePaginationSizeList[0]) {
        return this.availablePaginationSizeList[0]
      }

      for (let i = 0; i < this.availablePaginationSizeList.length; i++) {
        const size = this.availablePaginationSizeList[i]
        if (query > size) {
          query = size
        }
      }

      return query
    }

    private commitPaginationFromQuery() {
      let size = this.$route.query.size

      if (size && Number(size)) {
        size = Number(size)
        size = this.adjustPageSize(size)
        this.commitSetItemCountPerPage(size)
      }

      let page = this.$route.query.page

      if (page && Number(page)) {
        page = Number(page)

        if (page >= 0) {
          this.updateCurrentPage(page)
        }
      }
    }

    private commitSearchFromQuery() {
      let keyword = this.$route.query.search

      if (keyword && keyword.trim().length > 0) {
        this.commitUpdateSearchKeyword(keyword)
        this.searchInsertedKeyword = keyword
      }

      let category = this.$route.query.categoryId

      if (category && category.trim().length > 0 && category !== "all") {
        this.commitUpdateSearchCategory(category)
        this.searchSelectedCategory = category
      }
    }

    /**
     * event handlers
     */
    handlePaginationSizeChange(value) {
      this.commitSetItemCountPerPage(value)
      this.actionFetchPaginatedItems()
      this.setRouterQueryForPagination()
    }

    handleCurrentPageChange(value) {
      this.updateCurrentPage(value) // commit state
      this.actionFetchPaginatedItems()
      this.setRouterQueryForPagination()
    }

    handleSearchKeywordEnter() {
      const keyword = this.searchInsertedKeyword
      this.commitUpdateSearchKeyword(keyword)
      this.actionFetchPaginatedItems()
      this.setRouterQueryForSearch()
    }

    handleSearchCategoryChange() {
      const category = this.searchSelectedCategory
      this.commitUpdateSearchCategory(category)
      this.actionFetchPaginatedItems()
      this.setRouterQueryForSearch()
    }

    /**
     * helpers
     */

    updateCurrentPage(page) {
      this.commitUpdateCurrentPage(page)

      if (this.$refs.pagination) {
        // bug: https://qiita.com/hoshiaya/items/76cb4d0b00a502cb41e1
        this.$refs.pagination.internalCurrentPage = page
      }
    }

    setRouterQueryForPagination() {
      // build query
      let query = Object.assign({}, this.$route.query)

      const size = this.stateItemCountPerPage
      const page = this.stateCurrentPage

      query["size"] = size
      query["page"] = page

      this.$router.push({query: query,})
    }

    setRouterQueryForSearch() {
      let query = Object.assign({}, this.$route.query)

      const keyword = this.stateSearchKeyword
      const category = this.stateSearchCategory

      if (keyword && keyword.trim().length > 0) {
        query["search"] = keyword
      } else {
        delete query["search"]
      }

      if (category && category !== "all" &&
        Number(category) &&
        this.displayNameForSelectedCategory.trim() !== "") {
        query["categoryId"] = category
      } else {
        delete query["categoryId"]
      }

      this.$router.push({query: query,})
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

  .price-filter-popover-title {
    font-size: 16px;
  }

  .price-filter-popover-slider-text {
    font-size: 16px;
  }


</style>
