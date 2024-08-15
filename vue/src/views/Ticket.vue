<template>
    <div class="ticket-booking-container">


        <!-- 上午场次 -->
        <el-card class="ticket-card">
            <div class="ticket-info">
                <div>
                    <h3 class="ticket-title">上午场次</h3>
                    <p class="ticket-details">9:00 - 12:00</p>
                    <p class="ticket-details">成人票: ¥80 | 儿童票: ¥40</p>
                    <p class="ticket-details">剩余成人票: {{ adultTicketsm }}张 | 儿童票: {{ childTicketsm }}张</p>
                    <p class="ticket-details">适用日期: 2024年3月21日至2024年3月25日</p>
                </div>
                <div class="button-container">
                    <el-button type="primary" @click="buyTicketm">购买成人票</el-button>
                    <el-button type="primary" @click="buyTicketm">购买儿童票</el-button>
                </div>
            </div>
        </el-card>

        <!-- 下午场次 -->
        <el-card class="ticket-card">
            <div class="ticket-info">
                <div>
                    <h3 class="ticket-title">下午场次</h3>
                    <p class="ticket-details">13:00 - 18:00</p>
                    <p class="ticket-details">成人票: ¥100 | 儿童票: ¥50</p>
                    <p class="ticket-details">剩余成人票: {{ adultTicketsn }}张 | 儿童票: {{ childTicketsn }}张</p>
                    <p class="ticket-details">适用日期: 2024年3月21日至2024年3月25日</p>
                </div>
                <div class="button-container">
                    <el-button type="primary" @click="buyAdultTicket">购买成人票</el-button>
                    <el-button type="primary" @click="buyChildTicket">购买儿童票</el-button>
                </div>
            </div>
        </el-card>

        <!-- 优惠信息 -->
        <el-card class="discount-card">
            <div>
                <h3 class="discount-title">限时优惠</h3>
                <p class="discount-details">本周末成人票特价: ¥900</p>
                <p class="discount-details">购买前请查看活动规则</p>
                <el-button type="warning">查看详情</el-button>
            </div>
        </el-card>

    </div>
</template>

<script>
export default {
    name: "Ticket",
    data(){
        return {
            adultTicketsm: 80, // 成人票数量
            childTicketsm: 40, // 儿童票数量
            adultTicketsn: 80, // 成人票数量
            childTicketsn: 40, // 儿童票数量
        };
    },
    methods:{
        buyTicketm() {
            // 购买票时，剩余票数减1
            this.$message.success("购票成功")
            this.adultTicketsm -= 1;
            // 这里可以添加其他购票逻辑，例如发起购票请求等
        },
        buyTicketn() {
            // 购买票时，剩余票数减1
            this.adultTicketsn -= 1;
            // 这里可以添加其他购票逻辑，例如发起购票请求等
        },
    }
}
</script>

<style scoped>
.ticket-booking-container {
    width: 100%;
    padding: 20px;
}

.page-title {
    margin-top: 20px;
    font-size: 24px;
    color: #333;
}

.ticket-card {
    margin-top: 20px;
}

.ticket-info {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.ticket-title {
    font-size: 20px;
    margin-bottom: 5px;
}

.ticket-details {
    margin-bottom: 10px;
    color: #666;
}

.discount-card {
    margin-top: 20px;
    background-color: #f5f7fa;
}

.discount-title {
    margin-bottom: 10px;
    font-size: 20px;
}

.discount-details {
    color: #666;
    margin-bottom: 10px;
}


.image-slider img {
    width: 300px;
    height: auto;
    margin-right: 20px;
}
</style>