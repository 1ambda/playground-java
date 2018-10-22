<template>
  <el-row type="flex" justify="center">
    <el-col :xs="20" :sm="20" :md="18" :lg="18">
      <div class="cart">

        <el-table :data="tableData" style="width: 100%">

          <el-table-column type="selection" width="55"></el-table-column>

          <el-table-column label="Product" width="350">
            <template slot-scope="scope">
              <el-row>
                <el-col :xs="6" :sm="6" :md="6" :lg="6">
                  <span>IMAGE</span>
                </el-col>
                <el-col :xs="18" :sm="18" :md="18" :lg="18">
                  <span style="margin-left: 10px">{{ scope.row.name }}</span>
                </el-col>
              </el-row>
            </template>
          </el-table-column>

          <el-table-column label="Price" width="100">
            <template slot-scope="scope">
              <div>
                <span style="">{{ scope.row.price }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="Quantity" width="180">
            <template slot-scope="scope">
              <div>
                <el-input-number size="mini"
                                 min="0" max="99"
                                 controls-position="right"
                                 v-model="scope.row.quantity"></el-input-number>
              </div>
              <div style="margin-top: 10px;">
                <span >
                  {{ scope.row.quantity * scope.row.price }}
                </span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="Shipping" width="220">
            <template slot-scope="scope">
              <div>
                <span>{{ scope.row.shippingFee === 0 ? 'Free': scope.row.shippingFee }}</span>
              </div>
              <div>
                <span>2018. 10. 22 (expected)</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column fixed="right" width="160">
            <template slot-scope="scope">
              <div style="margin-left: 15px;">
                <el-button size="small" type="primary" plain
                           style="width: 70px;"
                           @click="handleEdit(scope.$index, scope.row)">
                  Order
                </el-button>
              </div>
              <div style="margin-left: 15px;">
                <el-button size="small" type="danger" plain
                           style="width: 70px; margin-top: 4px;"
                           @click="handleDelete(scope.$index, scope.row)">
                  Delete
                </el-button>
              </div>
            </template>
          </el-table-column>

        </el-table>

      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [
          {
            name: 'LG Notebook GRAM 13',
            description: 'LG gram Thin & Light Laptop - Up to 16.5 hrs, Thunderbolt 3, Finger Print Reader (Windows not installed)',
            quantity: 1,
            price: 1350000,
            shippingFee: 0,
            address: 'No. 189, Grove St, Los Angeles'
          },
          {
            name: 'AMD Ryzen 7 2700',
            description: '',
            quantity: 2,
            price: 359000,
            shippingFee: 0,
            address: 'No. 189, Grove St, Los Angeles'
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
      }
    }
  }
</script>
