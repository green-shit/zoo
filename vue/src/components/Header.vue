<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
        <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">后台管理</div>
        <div style="flex: 1"></div>
        <div style="width: 100px; padding-top: 30px">
            <el-dropdown>
            <span class="el-dropdown-link">
              用户名admin<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
                        <el-dropdown-item @click="handleLogout">退出系统</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import user from "@/views/User.vue";

export default {
    name: "Header",
    methods:{
        handleLogout(){
            sessionStorage.clear();
            this.$router.push('/login')
        }
    },
    computed: {
        user() {
            return user
        }
    }
}
</script>

<style scoped>

</style>
