//添加数据到表格
function showReocrd(pageNo, pageSize) {
    $.getJSON("/accountFenye",
        {
            pageNo: pageNo,
            pageSize: pageSize,
        },
        function (data) {
            //加载后台返回的List集合数据
            for (var i = 0; i < data.list.length; i++) {
                var td = $("<td style='text-align: center'></td>").text(data.list[i].id);
                var td2 = $("<td></td>").text(data.list[i].name);
                var td3 = $("<td></td>").text(data.list[i].pwd);
                var td4 = $("<td></td>").text(data.list[i].className);
                var td5 = $("<td></td>").text(data.list[i].studentID);
                var td7 = $("<td></td>").text(data.list[i].phone);
                var td8 = $("<td></td>").text(data.list[i].date);
                var td9 = $("<td></td>").text(data.list[i].role === 2 ? "普通用户":"管理员" );
                //管理员的修改权限
                var td6 = $("<td onclick='update_this(this)' style='cursor: pointer;text-align: center'></td>").html('<i class="layui-icon layui-icon-edit" "></i>');

                var tr = $("<tr></tr>").append(td, td2).append(td3).append(td4).append(td5).append(td7).append(td8).append(td9).append(td6);
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


//修改题目
function update_this(_this) {
    let id = $(_this).prev().prev().prev().prev().prev().prev().prev().prev().text();
    window.location.href="/back_system_edit_account/"+id;

}

