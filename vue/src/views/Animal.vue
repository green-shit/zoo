<template>
  <div style="width: 100%;padding: 10px">
      <div style="margin: 10px 0">
          <el-button type="primary" @click="add">新增</el-button>
      </div>
      <div style="margin: 10px 0">
          <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
          <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe style="width: 100%">
          <el-table-column prop="id" label="ID" sortable />
          <el-table-column prop="name" label="名字" />
          <el-table-column prop="species" label="物种" />
          <el-table-column prop="sex" label="性别" />
          <el-table-column prop="age" label="年龄" />
          <el-table-column fixed="right" label="操作" width="100%">
              <template #default="scope">
                  <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                  <el-popconfirm title="确认删除该条内容?" @confirm="handleDelete(scope.row.id)">
                      <template #reference>
                          <el-button type="text">删除</el-button>
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

      <div>
          <el-dialog v-model="dialogVisible" title="提示" width="30%">
              <el-form :model="form" label-width="120px">
                  <el-form-item label="名字">
                      <el-input v-model="form.name" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="物种">
                      <el-input v-model="form.species" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="性别">
                      <el-radio v-model="form.sex" label="雄" size="large" style="padding-bottom: 7px">雄</el-radio>
                      <el-radio v-model="form.sex" label="雌" size="large" style="padding-bottom: 7px">雌</el-radio>
                  </el-form-item>
                  <el-form-item label="年龄">
                      <el-input v-model="form.age" style="width: 80%"/>
                  </el-form-item>
                  <el-form-item label="负责人">
                      <el-input v-model="form.keeperId" style="width: 80%"/>
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
  name: 'Animal',
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
          request.get("/animal",{
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
          request.delete("/animal/"+id).then(res =>{
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
              request.put("/animal", this.form).then(res=>{
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
              request.post("/animal", this.form).then(res=>{
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
