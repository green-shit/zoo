<template>
  <div style="width: 1000px;padding: 10px">
      <div style="margin: 10px 0">
          <el-button type="primary" @click="add">新增</el-button>
      </div>
      <div style="margin: 10px 0">
          <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe style="width: 100%">
          <el-table-column prop="id" label="ID" sortable />
          <el-table-column prop="username" label="用户名" />
          <el-table-column prop="address" label="地址" />
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="role" label="角色">
              <template #default="scope">
                  <span v-if="scope.row.role===0">园长</span>
                  <span v-if="scope.row.role===1">管理员</span>
                  <span v-if="scope.row.role===2">游客</span>
              </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100%">
              <template #default="scope">
                  <el-button link type="success" v-if="scope.row.role===1" size="small" @click="showAnimal(scope.row.animalList)">管理动物</el-button>
                  <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                  <el-popconfirm title="确认删除该条内容?" @confirm="handleDelete(scope.row.id)">
                      <template #reference>
                          <el-button size="small" type="text">删除</el-button>
                      </template>
                  </el-popconfirm>
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

      <el-dialog title="动物管理" v-model="animalVis" width="30%">
          <el-table :data="animalList" stripe border>
              <el-table-column prop="id" label="ID"></el-table-column>
              <el-table-column prop="name" label="名字"></el-table-column>
              <el-table-column prop="species" label="物种"></el-table-column>
          </el-table>
      </el-dialog>

      <div>
          <el-dialog v-model="dialogVisible" title="提示" width="30%">
              <el-form :model="form" label-width="120px">
                  <el-form-item label="用户名">
                      <el-input v-model="form.username" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="角色">
                      <el-input v-model="form.role" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="电话">
                      <el-input v-model="form.phone" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="地址">
                      <el-input type="textarea" v-model="form.address" style="width: 80%"/>
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
  name: 'User',
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
          tableData:[],
          animalList:[],
          animalVis:false
      }
  },
    created() {
      this.load()
    },
    methods:{
        showAnimal(animal){
            this.animalList=animal
            this.animalVis=true
        },
      load(){
          request.get("/user",{
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
      handleEdit(row){
          this.form=JSON.parse(JSON.stringify(row))
          this.dialogVisible=true
      },
      handleDelete(id){
          console.log(id)
          request.delete("/user/"+id).then(res =>{
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
      add(){
          this.dialogVisible=true//打开弹窗
          this.form={}
      },
      save(){
          if(this.form.id){//id本来就存在，因此是一次更新操作
              request.put("/user", this.form).then(res=>{
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
              request.post("/user", this.form).then(res=>{
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
