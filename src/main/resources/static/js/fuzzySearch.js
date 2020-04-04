
//添加数据到表格
function showReocrd(pageNo, pageSize) {
    $.getJSON("/fuzzySearchFenye",
        {
            pageNo: pageNo,
            pageSize: pageSize,
        },
        function (data) {
            //加载后台返回的List集合数据
            for (var i = 0; i < data.list.length; i++) {
                var id = $("<td style='text-align: center'></td>").text(data.list[i].id);
                var course = $("<td style='display: none'></td>").text(data.list[i].course);
                var td_in = $("<td onclick='jump_detail(this)' style='cursor: pointer;text-align: center'></td>").html('<span style="color: blue;text-decoration: underline">进入答题</span>');
                var chapter = $("<td style='display: none'></td>").text(data.list[i].chapter);
                var title = $("<td></td>").text(data.list[i].title);
                var type = $("<td style='display: none'></td>").text(data.type);

                var tr = $("<tr></tr>").append(type, course).append(chapter).append(id).append(title).append(td_in);
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
                , last: 10                     //尾页文本
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

//点击选择题跳转到该题详情
function jump_detail(_this) {
    const type = $(_this).prev().prev().prev().prev().prev().text();
    const chapter = $(_this).prev().prev().prev().text();
    const course = $(_this).prev().prev().prev().prev().text();
    const id = $(_this).prev().prev().text();
    console.log(type + '==' + course + '===' + chapter+'===='+id);
    // window.location.href = "/choice_detail/" + course + "/" + chapter + "/" + id
    if (type === 'choice'){
        window.location.href = " /choice_detail/"+course+"/"+chapter+"/"+id;
    } else {
        window.location.href = " /summary_detail/"+course+"/"+chapter+"/"+id;
    }
}