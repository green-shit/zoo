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
                    <el-input v-model="form.password" placeholder="请输入密码" show-password>
                        <template #prefix>
                            <el-icon class="el-input__icon"><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%" type="primary" @click="login()">登录</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%" type="primary" @click="jumpRegister()">新用户注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name:"L",
    data() {
        return {
            form: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
        };
    },
    methods: {
        login() {
            // Add your login logic here
        },
        jumpRegister() {
            // Add your jump to register logic here
        }
    }
};
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