//添加数据到表格
function showReocrd(pageNo, pageSize) {
    $.getJSON("/recordLoginFenye",
        {
            pageNo: pageNo,
            pageSize: pageSize,
        },
        function (data) {
            //加载后台返回的List集合数据
            for (var i = 0; i < data.list.length; i++) {
                var td = $("<td style='text-align: center'></td>").text(data.list[i].id);
                var td2 = $("<td></td>").text(data.list[i].userName);
                var td3 = $("<td></td>").text(data.list[i].pwd);
                var td4 = $("<td></td>").text(data.list[i].userPwd);
                var td5 = $("<td></td>").text(data.list[i].source);
                var td7 = $("<td></td>").text(data.list[i].browser);
                var td8 = $("<td></td>").text(data.list[i].system);
                var td9 = $("<td></td>").text(data.list[i].ip);
                var td10;
                if (data.list[i].loginResult === "失败"){
                    td10 = $("<td style='color: red'></td>").text(data.list[i].loginResult);
                } else {
                    td10 = $("<td></td>").text(data.list[i].loginResult);
                }
                var td11 = $("<td></td>").text(data.list[i].address);
                var td12 = $("<td></td>").text(data.list[i].timeConsume);
                let date = new Date(data.list[i].date).format("yyyy-MM-dd hh:mm:ss");
                var td13 = $("<td></td>").text(date);

                var tr = $("<tr></tr>").append(td, td2).append(td3).append(td4).append(td5).append(td7).append(td8).append(td9).append(td10).append(td11).append(td12).append(td13);
                //将自己造出来的一行数据挂到tbody下
                $('tbody').append(tr);
            }
            $("#count").text(data.count)
        },
        "json"
    );
}

//先异步初始化加载首页和count，十条数据
showReocrd(1, 10);
//设置300毫秒后执行，才能获取count
setTimeout(function () {
    layui.use(['laypage', 'jquery'], function () {

        var laypage = layui.laypage, $ = layui.$;
        $(".page").each(function (i, the) {
            laypage.render({
                elem: the //注意，这里的 test1 是 ID，不用加 # 号
                , count: $("#count").text() //数据总数，从服务端得到
                , limit: 10                      //每页显示条数
                , limits: [10, 20, 30]
                , curr: 1                        //起始页
                , groups: 5                      //连续页码个数
                , prev: '上一页'                 //上一页文本
                , netx: '下一页'                 //下一页文本
                , first: 1                      //首页文本
                , last: 100                     //尾页文本
                , layout: ['prev', 'page', 'next', 'limit', 'refresh', 'skip']
                //跳转页码时调用
                , jump: function (obj, first) { //obj为当前页的属性和方法，第一次加载first为true
                    //非首次加载 do something
                    if (!first) {
                        //清空以前加载的数据
                        $('tbody').empty();
                        //调用加载函数加载数据
                        showReocrd(obj.curr, obj.limit);
                    }
                }
            });
        })
    });

}, 300);

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

