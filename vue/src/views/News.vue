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
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="author" label="作者" />
            <el-table-column prop="time" label="时间" />
            <el-table-column fixed="right" label="操作" width="100%">
                <template #default="scope">
                    <el-button type="text" @click="viewDetail(scope.row)">详情</el-button>
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
            <el-dialog v-model="dialogVisible" title="提示" width="50%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="标题">
                        <el-input v-model="form.title" style="width: 50%"/>
                    </el-form-item>
                    <div id="div1"></div>
                </el-form>
                <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">确认</el-button>
                </span>
                </template>
            </el-dialog>
            <el-dialog v-model="vis" title="详情" width="50%">
                <el-card>
                    <div v-html="detail.content"></div>
                </el-card>
            </el-dialog>
        </div>
    </div>
</template>

<script>

import request from "@/utils/request";
import E from 'wangeditor';
let editor;
export default {
    name: 'News',
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
            vis:false,
            detail:{}
        }
    },
    created() {
        this.load()
    },
    methods:{
        viewDetail(row){
            this.detail=row
            this.vis=true
        },
        load(){
            request.get("/news",{
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
            this.$nextTick(()=>{
                editor=new E('#div1')
                if(editor==null){
                    editor.create()
                    editor.txt.html(row.content)
                }else{
                    editor.destroy()
                    editor.create()
                    editor.txt.html(row.content)
                }
            })
        },
        handleDelete(id){
            console.log(id)
            request.delete("/news/"+id).then(res =>{
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
            //关联弹窗里面的div，new一个editor对象
            this.$nextTick(()=>{
                editor=new E('#div1')
                editor.create()
            })
        },
        save(){
            this.form.content=editor.txt.html()//获取编辑器里的值，然后赋予到实体，后面进行保存

            if(this.form.id){//id本来就存在，因此是一次更新操作
                request.put("/news", this.form).then(res=>{
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
                let userStr=sessionStorage.getItem("user")||"{}"//取出user信息
                let user=JSON.parse(userStr)
                this.form.author=user.username//只有新增news的时候才需要获取作者信息

                request.post("/news", this.form).then(res=>{
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