$(function () {
    //截取url中的课程参数
    let param = location.search;
    let coures = param.substring(param.length - 1);
    args = {
        "course": coures
    };
    $.getJSON("/exam_details", args, function (data) {
        var items = [];
        var side = [];
        var items2 = [];
        var side2 = [];
        //将选择题部分挂上去
        $.each(data.choices, function (key, val) {
            items.push('<li id="' + val.id + '">' +
                '<div class="test_content_nr_tt">' +
                '<i>' + val.countno + '</i><span>(4分)</span><font>' + val.title + '</font><b class="icon iconfont">&#xe881;</b>' +
                '</div>' +

                '<div class="test_content_nr_main">' +
                '<ul>' +

                '<li class="option">' +

                '<input type="radio" class="radioOrCheck" name="answer' + val.id + '" ' +
                'id="answer' + val.id + '" value="A" ' +
                '/> ' +


                '<label for="0_answer_1_option_1">' +
                '<p class="ue" style="display: inline;">' + val.a + '</p>' +
                '</label>' +
                '</li>' +

                '<li class="option">' +

                '<input type="radio" class="radioOrCheck" name="answer' + val.id + '" ' +
                'id="answer' + val.id + '" value="B" ' +
                '/>' +


                '<label for="0_answer_1_option_2">' +
                '  <p class="ue" style="display: inline;">' + val.b + '</p>' +
                ' </label>' +
                '  </li>' +

                ' <li class="option">' +

                '  <input type="radio" class="radioOrCheck" name="answer' + val.id + '" ' +
                'id="answer' + val.id + '" value="C" ' +
                '   />' +


                '  <label for="0_answer_1_option_3">' +
                '  <p class="ue" style="display: inline;">' + val.c + '</p>' +
                '  </label>' +
                '  </li>' +

                ' <li class="option">' +

                ' <input type="radio" class="radioOrCheck" name="answer' + val.id + '" ' +
                'id="answer' + val.id + '" value="D" ' +
                '  />' +


                '   <label for="0_answer_1_option_4">' +
                '   <p class="ue" style="display: inline;">' + val.d + '</p>' +
                '  </label>' +
                '  </li>' +

                '  </ul>' +
                '  </div>' +
                '  </li>'
            )
        });
        $("<ul/>", {
            "id": "choicePart",
            html: items.join("")
        }).appendTo(".choices");
        //将选择题边栏挂上去
        $.each(data.choices, function (key, val) {
            side.push('<li style="margin: 3px"><a href="#' + val.id + '">' + val.countno + '</a></li>')
        });
        $("<ul/>", {
            html: side.join("")
        }).appendTo(".side");
        //将简答题部分挂上去
        $.each(data.summaries, function (key, val) {
            items2.push('<li id="summary' + val.id + '">' +
                '  <div class="test_content_nr_tt"> ' +
                '  <i>' + val.countno + '</i><span>(10分)</span><font>' + val.title + '</font><b class="icon iconfont">&#xe881;</b> ' +
                '  </div> ' +
                '   <form class="layui-form"> ' +
                ' <div class="layui-form-item"> ' +
                ' <textarea name="' + val.id + '" required lay-verify="required" placeholder="请输入您的答案" class="layui-' + 'textarea"></textarea> ' +

                '        </div> ' +
                '  </form> ' +

                '  </li> '
            );
        });
        $("<ul/>", {
            "id": "summaryPart",
            html: items2.join("")
        }).appendTo(".summaries");
        //将简答题边栏挂上去
        $.each(data.summaries, function (key, val) {
            side2.push('<li style="margin: 3px"><a href="#summary' + val.id + '">' + val.countno + '</a></li>')
        });
        $("<ul/>", {
            html: side2.join("")
        }).appendTo(".side2");

        //设置选择题侧边栏变色
        $(".option").click(function () {
            let examId = $(this).parent().parent().parent().attr("id");
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        });
        //设置简答题侧边栏变色
        $(".layui-textarea").click(function () {
            let examId = $(this).parent().parent().parent().attr("id");
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        });

        //点击该行就选中radio
        $(".option").click(function () {
            $(this).children("input").attr("checked", "checked");

        })


    });


    $("input[name='test_jiaojuan']").click(function(){
        layer.open({
            anim: 1,
            title: ['提交答案','text-align:left;font-size:17px'],
            content: '确认提交吗',

            //发送ajax请求获取成绩和正确率
            yes: function(index, layero){
                //==========
                //循环获取选择题答案，构造数组
                var data = [];
                $("#choicePart li").not(".option").each(function (index, obj) {
                    data.push({
                        id: $(obj).attr("id"),
                        answer: $("input[type='radio']:checked", obj).val(),
                    })
                });
                console.log(data);
                //循环获取简答题答案，构造数组
                var data2 = [];
                $("#summaryPart li").each(function (index, obj) {
                    data2.push({
                        "id": $(obj).attr("id"),
                        "answer": $("textarea", obj).val(),
                    })
                });
                console.log(data2);

                //发送ajax请求获得成绩
                let args = {
                    "choice": data,
                    "summary": data2
                };
                $.ajax({
                    type: "post",
                    url: "test_scores",
                    data: JSON.stringify(args),
                    traditional: true, //数组防止深度序列化
                    contentType:"application/json",
                    success: function(data){
                        let choice=data.data.choiceRate;
                        let summary=data.data.summaryRate;
                        let score = data.data.score;

                        layer.open({
                            anim: 1,
                            area:['500px','240px'],
                            title: ['成绩','text-align:left;font-size:17px'],
                            content: '本次得分：'+score+'分</br>选择题正确率：'+choice+'%</br>简答题正确率：'+summary+'%</br>本次错题已记录在错题集中!',
                            yes: function(index, layero) {
                                window.location.href="/main";
                            }

                        });

                    }
                });
                //=======

                layer.close(index);
            }

        });
    });

});