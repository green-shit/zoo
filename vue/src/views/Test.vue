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
            <el-table-column prop="name" label="试卷名" />
            <el-table-column prop="testDate" label="考试时间" />
            <el-table-column prop="state" label="状态" />
            <el-table-column fixed="right" label="操作" width="100%">
                <template #default="scope">
                    <el-button type="text" v-if="user.role===1" size="small" @click="handleEdit(scope.row)">编辑</el-button>
<!--                    <el-button type="text" size="small" @click="composeTest(scope.row.id)">组卷</el-button>-->
                    <el-button type="text" v-if="user.role===2" size="small" @click="takeTest(scope.row)">开始答题</el-button>
                    <el-button type="text" size="small" v-if="user.role===1" @click="viewDetail(scope.row.questionList)">试卷详情</el-button>
                    <el-popconfirm title="确认删除该条内容?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="small" v-if="user.role===1" type="text">删除</el-button>
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

        <el-dialog title="试卷详情" v-model="QuestionVis" width="30%">
            <el-table :data="questionList" stripe border>
                <el-table-column prop="id" label="ID"></el-table-column>
                <el-table-column prop="content" label="题目内容"></el-table-column>
            </el-table>
        </el-dialog>

<!--        <div>
            <el-dialog v-model="dialogFormVisible" title="组卷" width="30%" :close-on-click-modal="false">
                <el-form :model="form1" label-width="120px">
                    <el-form-item label="题数">
                        <el-input v-model="form1.type1" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="generate">确认</el-button>
                </span>
                </template>
            </el-dialog>
        </div>-->

        <div>
            <el-dialog v-model="dialogVisible" title="提示" width="30%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="试卷名">
                        <el-input v-model="form.name" style="width: 80%"/>
                    </el-form-item>
                    <el-form-item label="考试时间">
                        <el-date-picker v-model="form.testDate" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
                    </el-form-item>
                    <el-form-item label="试卷状态">
                        <el-input v-model="form.state" style="width: 80%"/>
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

const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}

export default {
    name: 'Test',
    components: {
    },
    data() {
        return{
            user:{},
            /*form1:{},*/
            form:{},
            name:"",
            dialogVisible:false,
            dialogFormVisible:false,
            dialogFormVisible1:false,
            search:'',
            currentPage:1,
            pageSize:10,
            total:10,
            tableData:[],
            /*questions:[],*/
            questionList:[],
            QuestionVis:false
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
        viewDetail(question){
            this.questionList=question
            this.QuestionVis=true
        },
       /* composeTest(id){
            this.form1={type1:3,paperId:id}
            this.dialogFormVisible=true
        },*/
        load(){
            request.get("/test",{
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
            request.delete("/test/"+id).then(res =>{
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
        takeTest(){
            this.$router.push("/exam")
        },
        /*generate(){
            request.post("/paper/composeTest",this.form1).then(res=>{
                if(res.code==='0'){
                    this.$message({
                        type:"success",
                        message:"组卷成功"
                    })
                    this.dialogFormVisible=false
                }else{
                    this.$message({
                        type:"error",
                        message:res.msg
                    })
                }
            })
        },*/
        save(){
            if(this.form.id){//id本来就存在，因此是一次更新操作
                request.put("/test", this.form).then(res=>{
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
                request.post("/test", this.form).then(res=>{
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