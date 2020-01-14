//页面加载就计算出countNum
$(function () {
    let course = $("#getCourse").text();
    let chapter = $("#getChapter").text();
    const args = {
        "course": course,
        "chapter": chapter
    };
    $.getJSON("/choice_num", args, function (data) {
        $("#countNum").text(data)
    });

});

//判断答案对错
function judge() {
    let answer = $("input[name='answer']:checked").val();
    let id = $("#getId").text();
    const args = {
        "answer": answer,
        "id": id
    };
    $.getJSON("/answerJudge", args, function (data) {
        if (data.code === 1) {
            $.message('回答正确')
        } else {
            $.message({
                message: '回答错误',
                type: 'error'
            });
        }
        $("#true-answer").text('正确答案： ' + data.data.answer);
        if (data.data.analysis != null) {
            $("#analysis").text('题目解析： ' + data.data.analysis)
        }

    })

}

//判断当前题是否是最后一题
function verificationCountnum() {
    let countno = $("#getCountno").text();
    let countNum = $("#countNum").text();
    if (countno === countNum) {
        $.message({
            message: '最后一题啦',
            type: 'warning'
        });
        return false
    } else {
        return true

    }
}
