new Vue({
    el: '#app',
    data: function() {
        return { visible: false }
    }
})


var vm = new Vue({
    el: '#vue_det',
    data: {
        site: "菜鸟教程",
        url: "www.runoob.com",
        alexa: "10000"
    },
    methods: {
        details: function () {
            return this.site + " - 学的不仅是技术，更是梦想！";
        }
    }
})

new Vue({
    el: '#click1',
    data:{
        int: 20
    },
    methods: {
        vue_click: function () {
            return data.int + 1;
        }
    }
})


var me =  {
    data() {
        return {
            activeIndex: '1',
            activeIndex2: '1'
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        }
    }
}
var Ctor1 = Vue.extend(me)
new Ctor1().$mount('#tab1')

// var Main = {
//     data() {
//         const item = {
//             date: '2016-05-02',
//             name: '王小虎',
//             address: '上海市普陀区金沙江路 1518 弄'
//         };
//         return {
//             tableData: Array(20).fill(item)
//         }
//     }
// };
// var Ctor = Vue.extend(Main)
// new Ctor().$mount('#tab')




/*apptab*/
var Main = {
    data() {
        return {
            editableTabsValue: '2',
            editableTabs: [{
                title: 'Tab 1',
                name: '1',
                content:{
                    'todo-item': {
                        template: '<el-tag type="success">标签二</el-tag>'
                    }
                }
            }, {
                title: 'Tab 2',
                name: '2',
                content: 'Tab 2 content'
            }],
            tabIndex: 2
        }
    },
    methods: {
        addTab(targetName) {

            // alert(targetName);
            var ss = 'www.baidu.com';
            var ppp = "<iframe src="+ss+ " frameborder='0' scrolling='false' style='width:1450px;height: 600px;'></iframe>";

            var mm ={
                'todo-item': {
                    template: '<el-tag type="success">标签二</el-tag>'
                }
            };

                let newTabName = ++this.tabIndex + '';
            this.editableTabs.push({
                title: '用户查询',
                name: newTabName,
                contents:mm

                // components: {
                //     'todo-item': {
                //         template: '<el-tag type="success">标签二</el-tag>'
                //     }
                // }

            });
            this.editableTabsValue = newTabName;
        },
        removeTab(targetName) {
            let tabs = this.editableTabs;
            let activeName = this.editableTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }

            this.editableTabsValue = activeName;
            this.editableTabs = tabs.filter(tab => tab.name !== targetName);
        }
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#tab')