<template>
    <div style="width: 100%;padding: 10px">
        <div style="margin: 10px 0">
            看看它到底是何方神圣吧！上传图片，识别动物种类。
            <el-button type="primary" @click="add">来试试吧</el-button>
        </div>
        <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="picture" label="动物图片">
                <template #default="scope">
                    <el-image style="width: 800px; height: 600px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]" preview-teleported="true"/>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100%">
                <template #default="scope">
                    <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确认删除该条内容?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button type="text">删除</el-button>
                        </template>
                    </el-popconfirm>
                    <el-button type="text" @click="viewResult(scope.row.id)">识别</el-button>
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
                    <el-form-item label="动物图片">
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
    name: "Recognition",
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
        this.load()
    },
    methods:{
        load(){
            request.get("/recognition",{
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
        viewResult(id){
            const PAT = '011aea285486439c8868964383a49260';
            const USER_ID = 'f3kfhrob1m29';
            const APP_ID = 'AnimalRecognition';
            const MODEL_ID = 'general-image-recognition';
            const MODEL_VERSION_ID = 'aa7f35c01e0642fda5cf400f543e7c40';
            const IMAGE_URL = 'D:/desktop/sloth.jpeg';

            const raw = JSON.stringify({
                "user_app_id": {
                    "user_id": USER_ID,
                    "app_id": APP_ID
                },
                "inputs": [
                    {
                        "data": {
                            "image": {
                                "url": IMAGE_URL
                            }
                        }
                    }
                ]
            });

            const requestOptions = {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Authorization': 'Key ' + PAT
                },
                body: raw
            };

            fetch("https://api.clarifai.com/v2/models/" + MODEL_ID + "/versions/" + MODEL_VERSION_ID + "/outputs", requestOptions)
                .then(response => response.text())
                .then(result => console.log(result))
                .catch(error => console.log('error', error));
        },
        handleDelete(id){
            console.log(id)
            request.delete("/recognition/"+id).then(res =>{
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
        handleEdit(row){
            this.form=JSON.parse(JSON.stringify(row))
            this.dialogVisible=true
        },
        filesUploadSuccess(res){
            console.log(res)
            this.form.picture=res.data
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
                request.put("/recognition", this.form).then(res=>{
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
                request.post("/recognition", this.form).then(res=>{
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

<style scoped>

</style>