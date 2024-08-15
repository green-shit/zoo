<template>
    <div style="width: 100%;padding: 10px">
        <div style="margin: 10px 0">
            <el-button type="primary" @click="add" v-if="user.role===1">新增</el-button>
            <el-button type="primary" @click="viewCart" v-if="user.role===2">查看购物车</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>
        <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" sortable />
            <el-table-column prop="picture" label="商品图片">
                <template #default="scope">
                    <el-image style="width: 100px; height: 100px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]" preview-teleported="true"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="商品名" />
            <el-table-column prop="price" label="价格" />
            <el-table-column prop="putawayTime" label="上架时间" />
            <el-table-column fixed="right" label="操作" width="100%">
                <template #default="scope">
                    <el-button type="text" v-if="user.role===1" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确认删除该条内容?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button type="text" v-if="user.role===1">删除</el-button>
                        </template>
                    </el-popconfirm>
                    <el-button type="text" v-if="user.role===2" @click="buy(scope.row.id)">购买</el-button>
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
                    <el-form-item label="商品名">
                        <el-input v-model="form.name" style="width: 80%"/>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="form.price" style="width: 80%"/>
                    </el-form-item>
                    <el-form-item label="上架时间">
                        <el-date-picker v-model="form.putawayTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
                    </el-form-item>
                    <el-form-item label="商品图片">
                        <el-upload ref="upload" action="http://localhost:9090/files/upload" :on-success="filesUploadSuccess">
                            <el-button type="primary">点击上传</el-button>
                        </el-upload>
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
    name: 'Goods',
    components: {
    },
    computed: {
    },
    data() {
        return{
            user:{},
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
        let userStr=sessionStorage.getItem("user") || "{}"
        this.user=JSON.parse(userStr)
        //确认登录用户合法
        request.get("/user/"+this.user.id).then(res =>{
            if(res.code==='0'){
                this.user=res.data
            }
        })

        this.load()
    },
    methods:{
        buy(goodsId){
            request.post("/cart/buy?goodsId="+goodsId).then(res =>{
                if (res) {
                    this.$message.success("下单成功")
                    this.load()
                } else {
                    this.$message.error("下单失败")
                }
            })
        },
        viewCart(){
            this.$router.push("/orders")
        },
        filesUploadSuccess(res){
            console.log(res)
            this.form.picture=res.data
        },
        load(){
            request.get("/goods",{
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
            request.delete("/goods/"+id).then(res =>{
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
            this.pageNum=pageNum
            this.load()
        },
        add(){
            this.dialogVisible=true//打开弹窗
            this.form={}
        },
        save(){
            if(this.form.id){//id本来就存在，因此是一次更新操作
                request.put("/goods", this.form).then(res=>{
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
                request.post("/goods", this.form).then(res=>{
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