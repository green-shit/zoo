<template>
  <div style="width: 100%;padding: 10px">
      <div style="margin: 10px 0">
          <el-button type="primary" @click="viewShop">查看商品列表</el-button>
      </div>
      <div style="margin: 10px 0">
          <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe style="width: 100%">
          <el-table-column label="id" prop="id" />
          <el-table-column label="订单名称" prop="name" />
          <el-table-column label="订单编号" prop="orderId" />
          <el-table-column label="支付宝订单号" prop="alipayNo" />
          <el-table-column label="总价格" prop="total" />
          <el-table-column label="创建时间" prop="createTime" />
          <el-table-column label="支付时间" prop="payTime" />
          <el-table-column label="订单状态" prop="state" />
          <el-table-column label="操作">
              <template v-slot="scope">
                  <el-button @click="pay(scope.row)" type="primary" size="small" :disabled="scope.row.state">支付</el-button>
                  <el-button @click="cancel(scope.row.id)" type="danger" size="small" :disabled="scope.row.state">取消</el-button>
              </template>
          </el-table-column>
      </el-table>

      <div style="margin:10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
          />
      </div>

      <div>
          <el-dialog v-model="dialogVisible" title="提示" width="30%">
              <el-form :model="form" label-width="120px">
                  <el-form-item label="订单名称">
                      <el-input v-model="form.name" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="订单编号">
                      <el-input v-model="form.orderId" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="总价格">
                      <el-input v-model="form.total" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="创建时间">
                      <el-input v-model="form.createTime" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="订单状态">
                      <el-input v-model="form.state" style="width: 80%"/>
                  </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">确认</el-button>
                </span>
              </template>
          </el-dialog>
      </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'Orders',
  components: {
  },
  data() {
      return{
          form:{},
          dialogVisible:false,
          search:'',
          currentPage:1,
          pageSize:10,
          total:10,
          tableData:[]
      }
  },
    created() {
      this.load()
    },
    methods:{
      load(){
          request.get("/orders",{
              params:{
                  pageNum:this.currentPage,
                  pageSize:this.pageSize,
                  search:this.search
              }
          }).then(res => {
              console.log(res)
              this.tableData=res.data.records
              this.total=res.data.total
          })
      },
        viewShop(){
            this.$router.push("/goods")
        },
      handleDelete(id){
          console.log(id)
          request.delete("/orders/"+id).then(res =>{
              if(res.code==='0'){
                  this.$message({
                      type:"success",
                      message:"更新成功"
                  })
              }else{
                  this.$message({
                      type:"error",
                      message:res.msg
                  })
              }
              this.load()//刷新数据
          })
      },
      handleSizeChange(pageSize){//改变当前每页条数
          this.pageSize=pageSize
          this.load()
      },
      handleCurrentChange(pageNum){//改变当前所在页码
          this.currentPage=pageNum
          this.load()
      },
        pay(row) {
            window.open("http://localhost:9090/alipay/pay?subject=" + row.name + "&traceNo=" + row.orderId +
                "&totalAmount=" + row.total)
            this.$message.success("请求支付宝成功")
        },
        cancel(row) {
            request.get("/orders",{
                params:{
                    pageNum:this.currentPage,
                    pageSize:this.pageSize,
                    search:this.search
                }
            }).then(res => {
                console.log(res)
                this.tableData=res.data.records
                this.total=res.data.total
            })
        },
      save(){
          if(this.form.id){//id本来就存在，因此是一次更新操作
              request.put("/orders", this.form).then(res=>{
                  console.log(res)
                  if(res.code==='0'){
                      this.$message({
                          type:"success",
                          message:"更新成功"
                      })
                  }else{
                      this.$message({
                          type:"error",
                          message:res.msg
                      })
                  }
                  this.load()//刷新数据
                  this.dialogVisible=false//关闭弹窗
              })
          }else{//id本不存在，是新增
              request.post("/orders", this.form).then(res=>{
                  console.log(res)
                  if(res.code==='0'){
                      this.$message({
                          type:"success",
                          message:"新增成功"
                      })
                  }else{
                      this.$message({
                          type:"error",
                          message:res.msg
                      })
                  }
                  this.load()
                  this.dialogVisible=false
              })
          }
      }
  }
}
</script>
