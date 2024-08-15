<template>
    <div>
        <el-affix position="top">
        <!--      头部-->
        <div style="height: 50px;background-color:white">
            <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
                <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">动物园欢迎您</div>
                <div style="flex: 1"></div>
                <div style="width: 100px; padding-top: 30px">
                    <el-dropdown>
            <span class="el-dropdown-link">
              {{user?.username}}<i class="el-icon-arrow-down el-icon--right"></i>
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

        </div>
        </el-affix>

        <!--        主体-->

        <div style="display: flex">
            <el-affix :offset="50">
            <!--        侧边栏 -->
            <Aside />
        </el-affix>
            <!--        内容区域-->
                <router-view style="flex: 1"/>
        </div>
    </div>
</template>

<script>
import Aside from "@/components/Aside.vue";

export default {
    name: "Layout",
    components: {
        Aside
    },
    props:['user'],
    data(){
        return{
            user:{}
        }
    },
    created(){
        let userStr = sessionStorage.getItem("user") || "{}"
        this.user = JSON.parse(userStr)
    },
    methods:{
        handleLogout(){
            sessionStorage.clear();
            this.$router.push('/login')
        }
    }
}
</script>

<style scoped>

</style>