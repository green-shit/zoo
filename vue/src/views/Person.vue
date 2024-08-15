<template>
    <div style="width: 100%; height: 100vh;background-color:gray;overflow: hidden">
        <div style="width: 600px;margin: 100px auto">
            <div style="color: #cccccc;font-size: 30px;text-align: center;padding: 30px 0">个人信息界面</div>
            <div>
                <el-card style="width: 600px;margin-right: 0px">
                    <el-form ref="form" :model="form" abel-width="80px">
                        <el-form-item label="用户名">
                            <el-input v-model="form.username" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="角色">
                            <el-input v-model="form.role"></el-input>
                        </el-form-item>
                        <el-form-item label="电话">
                            <el-input v-model="form.phone"></el-input>
                        </el-form-item>
                        <el-form-item label="地址">
                            <el-input v-model="form.address"></el-input>
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="form.password" show-password></el-input>
                        </el-form-item>
                    </el-form>
                    <div style="text-align: center">
                        <el-button type="primary" @click="update()">确定修改</el-button>
                    </div>
                </el-card>
            </div>
        </div>
    </div>


</template>


<script>
import request from "@/utils/request";

export default {
    name: "Person",
    data(){
        return{
            form:{}
        }
    },
    created() {
        let str=sessionStorage.getItem("user")||"{}"
        this.form=JSON.parse(str)
    },
    methods:{
        update(){
            request.put("/user",this.form).then(res=>{
                console.log(res)
                if(res.code==='0'){
                    this.$message({
                        type:"success",
                        message:"个人信息更新成功"
                    })
                    sessionStorage.setItem("user",JSON.stringify(this.form))
                }else{
                    this.$message({
                        type:"error",
                        message:res.msg
                    })
                }
            })
        }
    }
}
</script>
