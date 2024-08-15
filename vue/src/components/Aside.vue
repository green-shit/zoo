<template>
  <div>
      <el-menu style="width: 200px; min-height: calc(100vh - 50px)" class="el-menu-vertical-demo"
               :default-active="path" router>
      <el-sub-menu index="1" v-if="user.role!=2">
              <template #title>系统管理</template>
              <el-menu-item index="/user">用户管理</el-menu-item>
              <el-menu-item index="/animal">动物管理</el-menu-item>
          <el-sub-menu index="2">
              <template #title>活动管理</template>
              <el-menu-item index="/question">题库管理</el-menu-item>
              <el-menu-item index="/test">考试管理</el-menu-item>
          </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="/ticket">现在购票</el-menu-item>
          <el-menu-item index="/intro">动物园简介</el-menu-item>
          <el-menu-item index="/goods">纪念品小商城</el-menu-item>
          <el-menu-item index="/news">新闻一览</el-menu-item>
          <el-menu-item v-if="user.role==2" index="/test">参与活动获优惠</el-menu-item>
      </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: "Aside",
    data(){
        return{
            user:{},
            path:this.$route.path//设置高亮侧边栏
        }
    },
    created() {
        let userStr=sessionStorage.getItem("user") || "{}"
        this.user=JSON.parse(userStr)
        //确认登录用户合法
        request.get("/user/"+this.user.id).then(res =>{
            if(res.code==='0'){
                this.user=res.data
            }
        })
    }
}
</script>

<style scoped>

</style>