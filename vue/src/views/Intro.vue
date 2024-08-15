<template>
  <div style="width: 100%;padding: 10px">
      <el-row :gutter="10">
          <el-col :span="12">
              <el-card>
                  <div style="width: 100%;height: 400px" id="line"></div>
              </el-card>
          </el-col>
          <el-col :span="12">
              <el-card>
                  <div style="width: 100%;height: 400px" id="pie"></div>
              </el-card>
          </el-col>
      </el-row>

      <el-row :gutter="10" style="margin: 10px 0">
          <el-col :span="12">
              <el-card>
                  <div style="width: 100%;height: 400px" id="stack"></div>
              </el-card>
          </el-col>
          <el-col :span="12">
              <el-card>
                  <div style="width: 100%;height: 400px" id="Nightingale"></div>
              </el-card>
          </el-col>
      </el-row>
      <el-row :gutter="10" style="margin: 10px 0">
          <el-col :span="24">
              <el-card>
                  <div style="width: 100%;height: 800px" id="path">
                      <img src="../assets/Map_of_Iceland.jpeg">
                  </div>
              </el-card>
          </el-col>
      </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
const option1 = {
    title: {
        text: '动物园动物总数与来访游客总数年表',
        left:'center'
    },
    legend: {
        orient:'vertical',
        left:'left'
    },
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: ['1990', '1995', '2000', '2005', '2010', '2015', '2020']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'游客数',
            data: [150, 230, 224, 218, 520, 471, 623],
            type: 'line'
        },{
            name:'动物数',
            data: [100, 230, 243, 281, 531, 444, 620],
            type: 'line'
        }
    ]
}
const option2 = {
    title: {
        text: '纪念品销售情况',
        left:'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        top: '5%',
        left: 'center'
    },
    series: [
        {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
            },
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 40,
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                { value: 1048, name: '零食' },
                { value: 735, name: '玩偶' },
                { value: 580, name: '服饰' },
                { value: 484, name: '海报' },
                { value: 300, name: '书籍' }
            ]
        }
    ]
}
const option3={
    color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
    title: {
        text: '各馆温度变化图',
        left:'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            data: ['8:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00']
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '海洋馆',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(128, 255, 165)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(1, 191, 236)'
                    }
                ])
            },
            emphasis: {
                focus: 'series'
            },
            data: [14, 23, 19, 26, 28, 34, 25]
        },
        {
            name: '植物园',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(0, 221, 255)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(77, 119, 255)'
                    }
                ])
            },
            emphasis: {
                focus: 'series'
            },
            data: [12, 28, 11, 23, 22, 34, 31]
        },
        {
            name: '熊猫馆',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(55, 162, 255)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(116, 21, 219)'
                    }
                ])
            },
            emphasis: {
                focus: 'series'
            },
            data: [32, 32, 21, 33, 19, 30, 22]
        },
        {
            name: '爬行馆',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(255, 0, 135)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(135, 0, 157)'
                    }
                ])
            },
            emphasis: {
                focus: 'series'
            },
            data: [22, 32, 21, 34, 19, 23, 20]
        },
        {
            name: '大象馆',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            label: {
                show: true,
                position: 'top'
            },
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(255, 191, 0)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(224, 62, 76)'
                    }
                ])
            },
            emphasis: {
                focus: 'series'
            },
            data: [22, 23, 21, 24, 21, 29, 15]
        }
    ]
}
const option4 = {
    title: {
        text: '园区动物数量详情',
        subtext: '海洋馆详情见右',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    series: [
        {
            name: 'Radius Mode',
            type: 'pie',
            radius: [20, 140],
            center: ['35%', '50%'],
            roseType: 'radius',
            itemStyle: {
                borderRadius: 5
            },
            label: {
                show: true
            },
            emphasis: {
                label: {
                    show: true
                }
            },
            data: [
                { value: 50, name: '海洋馆' },
                { value: 33, name: '植物园' },
                { value: 28, name: '熊猫馆' },
                { value: 22, name: '爬行馆' },
                { value: 20, name: '大象馆' }
            ]
        },
        {
            name: 'Area Mode',
            type: 'pie',
            radius: [20, 40],
            center: ['80%', '50%'],
            roseType: 'area',
            itemStyle: {
                borderRadius: 5
            },
            data: [
                { value: 6, name: '海豚'},
                { value: 4, name: '海蛇' },
                { value: 6, name: '海星' },
                { value: 4, name: '鱼' },
                { value: 10, name: '珊瑚' },
                { value: 2, name: '海狗' },
                { value: 2, name: '海豹' },
                { value: 16, name: '企鹅' }
            ]
        }
    ]
};
const option5 = {
    title: {
        text: '园区地图'
    },
    xAxis: {
        max:10,
        min:0,
        show:false
    },
    yAxis: {
        max:10,
        min:0,
        show:false
    },
    tooltip: {
        trigger: 'item',
        backgroundColor: '#fff', // 悬浮框背景色
        borderColor: '#000', // 悬浮框边框颜色
        borderWidth: 1, // 悬浮框边框宽度
        textStyle: { // 悬浮框文字样式
            color: '#000',
            fontSize: 12
        }
    },
    graphic: [
        {
            type: "image",
            left: "0",
            top: "0",
            z: -10,
            bounding: "raw",
            rotation: 0, //旋转
            origin: [510, 100], //中心点
            scale: [0.8, 0.8], //缩放
            style: {
                // 背景图
                image: require("../assets/Map_of_Iceland.jpeg"),
                opacity: 1,
            },
        },
    ],
    series: [
        {
            symbolSize: 40,
            color: 'rgb(55, 162, 255)',
            itemStyle:{
                normal:{
                    shadowBlur:10,
                    shadowColor:'rgb(116, 21, 219)'
                }
            },
            data: [
                {value:[2.0, 2.0],tooltip: '售票处'},
                {value:[6.0, 2.0],tooltip: '海洋馆'},
                {value:[5.6, 4.7],tooltip: '大象馆'},
                {value:[3.0, 6.81],tooltip: '熊猫馆'},
                {value:[4.0, 5.33],tooltip: '爬行馆'},
                {value:[4.4, 8.66],tooltip: '植物园'},
                {value:[4.4, 1.1],tooltip: '游客中心'},
            ],
            type: 'scatter'
        }
    ]
};

export default {
    name: "Intro",
    data(){
        return{}
    },
    mounted(){//页面元素全部加载完成再初始化
        //第一个图：游客与动物数目折线图
        let lineDom = document.getElementById('line');
        let lineChart = echarts.init(lineDom);
        lineChart.setOption(option1);

        //第二个饼图，纪念品销量
        let pieDom = document.getElementById('pie');
        let pieChart = echarts.init(pieDom);
        pieChart.setOption(option2);

        //第三个饼图，各馆温度图，渐变堆叠面积图
        let stackDom = document.getElementById('stack');
        let stackChart = echarts.init(stackDom);
        stackChart.setOption(option3);

        //第四个饼图，园区动物数量图，Nightingale图
        let NightingaleDom = document.getElementById('Nightingale');
        let NightingaleChart = echarts.init(NightingaleDom);
        NightingaleChart.setOption(option4);

        //第五个地图
        let pathDom = document.getElementById('path');
        let pathChart = echarts.init(pathDom);
        pathChart.setOption(option5);
    }
}
</script>

<style scoped>

</style>