<template>
    <div class="exam-paper">
        <div class="paper-list" style="margin-top: 50px">
            <el-card v-for="(question, index) in questions" :key="index" class="question-card">
                <div slot="header" class="question-header">题目{{ index + 1 }}</div>
                <div class="question-content">
                    <p>{{ question.title }}</p>
                    <el-radio-group v-model="question.selectedOption">
                        <el-radio v-for="(option, optionIndex) in question.options" :key="optionIndex" :label="option">
                            {{ option }}
                        </el-radio>
                    </el-radio-group>
                </div>
            </el-card>
        </div>
        <el-button type="primary" @click="submitAnswers" style="margin-top: 10px;margin-left: 600px">提交</el-button>
    </div>
</template>

<script>
export default {
    name: "Exam",
    data() {
        return {
            dVisible:false,
            questions: [
                {
                    title: '以下哪种动物在北极没有分布？',
                    options: ['选项A——企鹅', '选项B——北极熊', '选项C——白鲸', '选项D——雪鸮'],
                    selectedOption: '' // 存储用户选择的选项
                },
                {
                    title: '以下属于我们动物园的园区的是？',
                    options: ['选项A——爬行馆', '选项B——水母馆', '选项C——蛇馆', '选项D——河马馆'],
                    selectedOption: '' // 存储用户选择的选项
                },
                {
                    title: '我们动物园什么时候不开放？',
                    options: ['选项A——节假日', '选项B——每一天', '选项C——没有任何时候', '选项D——周三'],
                    selectedOption: '' // 存储用户选择的选项
                }
                // 添加更多题目...
            ]
        };
    },
    methods: {
        submitAnswers() {
            // 在这里处理用户的答案提交逻辑，你可以将用户选择的选项发送到服务器或者进行其他操作
            console.log(this.questions);
            this.$message.success("您获得了20分！恭喜获得优惠劵一张。")

        }
    }
}
</script>

<style scoped>
.exam-paper {
    background-image: url('../assets/sloth.png'); /* 设置背景图片的URL */
    background-size: cover; /* 让背景图片铺满整个容器 */
    background-position: center; /* 将背景图片居中显示 */
    padding: 20px; /* 添加内边距，使内容不紧贴边缘 */
}
.paper-list {
    background-color: rgba(255, 255, 255, 0.5); /* 设置半透明背景颜色 */
    padding: 20px; /* 添加内边距，使内容不紧贴边缘 */
}
.question-card {
    margin-bottom: 20px;
}

.question-header {
    font-size: 18px;
    font-weight: bold;
}

.question-content {
    padding: 15px;
}
</style>