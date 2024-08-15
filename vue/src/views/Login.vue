<template>
  <div class="login-container">
      <div class="login-form">
          <div class="title">请登录</div>
          <el-form ref="form" :model="form" size="normal" :rules="rules">
              <el-form-item prop="username">
                  <el-input v-model="form.username" placeholder="请输入用户名">
                      <template #prefix>
                          <el-icon class="el-input__icon"><User /></el-icon>
                      </template>
                  </el-input>
              </el-form-item>
              <el-form-item prop="password">
                  <el-input v-model="form.password" placeholder="请输入密码" show-password><template #prefix><el-icon class="el-input__icon"><Lock /></el-icon></template></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button style="width: 100%" type="primary" @click="login()">登录</el-button>
              </el-form-item>
              <el-form-item>
                  <el-button style="width: 100%" type="primary" @click="jumpRegister()">新用户注册</el-button>
              </el-form-item>
          </el-form>
      </div>
<!--      <div>
          <img src="../pictures/login_1.png"/>
      </div>-->
  </div>
</template>

<script>
import {User,Lock} from '@element-plus/icons-vue'
import request from "@/utils/request";
export default {
    name: "Login",
    components:{Lock, User},
    data(){
        return{
            form:{},//form先定义成一个对象
            rules:{
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ]
            }
        }
    },
    methods:{
        login(){
            this.$refs['form'].validate((valid)=>{
                if(valid){
                    request.post("/user/login",this.form).then(res=>{
                        if(res.code==='0'){
                            this.$message({
                                type:"success",
                                message:"登录成功"
                            })
                            sessionStorage.setItem("user",JSON.stringify(res.data))//保存用户信息
                            this.$router.push("/") //登陆成功后跳转主页
                        }else{
                            this.$message({
                                type:"error",
                                message:res.msg
                            })
                        }
                    })
                }
            })
        },
        jumpRegister(){
            this.$router.push("/register")
        }
    }
}
</script>
<style>
.login-container {
    width: 100%;
    height: 100vh;
    background-image: url('../assets/sloth.png'); /* 替换为你的图片路径 */
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-form {
    background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
    padding: 20px;
    border-radius: 10px;
    width: 400px;
}

.title {
    color: #333;
    font-size: 30px;
    text-align: center;
    padding: 30px 0;
}
</style>
<style scoped>

</style>