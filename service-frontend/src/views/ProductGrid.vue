<template>
  <div>
    <el-row>
      <el-col v-for="(item, index) in productGridList" :key="index"
              :xs="12" :sm="8" :md="6" :lg="6">
        <div class="grid-item-container" style="margin-bottom: 30px;">

          <!-- grid item image -->
          <div class="grid-item-image-container">
            <div class="grid-item-image">
              IMAGE
            </div>
          </div>

          <!-- grid item detail -->
          <div class="grid-item-detail"
               style="margin-top: 20px; padding-left: 20px; padding-right: 20px; min-height: 80px;">

            <!-- name -->
            <div class="grid-item-detail-name" style="">
              {{ item.name }}
            </div>

            <!-- review price -->
            <div class="grid-item-detail-price" style="margin-top: 7px;">
              ₩{{ item.price }}
            </div>

            <!-- review rate -->
            <div class="grid-item-detail-review-rate" style="margin-top: 7px;">
              <el-rate v-model="item.reviewRate"
                       disabled
                       show-score
                       text-color="#ff9900"
                       score-template="{value}">
              </el-rate>
            </div>

            <!-- tags -->
            <div class="grid-item-detail-tags" style="margin-top: 5px;">
              <el-tag v-for="(tag, index) in item.tags" :key="index"
                      type="primary" size="small" style="margin-right: 5px;">
                {{tag}}
              </el-tag>
            </div>

          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
  import {Component, Vue, Watch} from "vue-property-decorator"
  import * as States from "@/store/state_type"
  import {Action, Getter, Mutation, State,} from "vuex-class"
  import {ProductDTO} from "../generated/swagger"

  @Component({
    name: "ProductGrid",
  })
  export default class ProductGrid extends Vue {
    public $refs: any
    public $notify: any
    public $router: any

    /**
     * vuex mappers and computed properties
     */

    @State(States.PRODUCT__FETCHED_ITEMS) public products

    get productGridList() {
      return this.products.map(p => this.buildRenderableProduct(p))
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

    buildRenderableProduct(product: ProductDTO) {
      const tags = [
        ["shipping", "event", "coupon",],
        ["shipping", "coupon",],
        ["shipping", "event",],
        ["shipping",],
      ]

      return {
        productId: product.id,
        name: product.name,
        description: product.description,
        categoryId: product.categoryId,
        categoryPath: product.categoryPath,
        categoryDisplayName: product.categoryDisplayName,
        price: product.price,
        imageId: product.imageId,
        imageType: product.imageType,
        imagePath: product.imagePath,
        shippingFee: 0,
        reviewRate: product.name.length % 5,
        tags: tags[product.name.length % 4],
      }
    }
  }
</script>

<style scoped>
  .grid-item-container {
  }

  .grid-item-image-container {
    text-align: center;
    vertical-align: middle;
    line-height: normal;
  }

  .grid-item-image {
    display: flex;
    justify-content: center; /* align text horizontal */
    align-items: center; /* align text vertical */
    margin: 0 auto; /* align image into center in horizontally */

    border: 2px dashed #7ec4ff;
    width: 200px;
    height: 200px;
  }

  .grid-item-detail {
    vertical-align: middle;
    line-height: normal;
  }

  .grid-item-detail-name {
    font-size: 16px;
    font-weight: 400;
  }

  .grid-item-detail-price {
    font-size: 17px;
    font-weight: 400;
    font-family: 'Avenir', Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    color: #7f33f1;
  }

  .grid-item-detail-review-rate {
  }

  .el-icon-star-on {
    font-size: 15px;
  }

  .grid-item-detail-tags {

  }

</style>
