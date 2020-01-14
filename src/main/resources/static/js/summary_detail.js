//页面加载就计算出countNum
$(function () {
    let course = $("#getCourse").text();
    let chapter = $("#getChapter").text();
    const args = {
        "course": course,
        "chapter": chapter
    };
    $.getJSON("/summary_num", args, function (data) {
        $("#countNum").text(data)
    });

});

//判断答案对错
function judge() {
    let answer = $("textarea[name='answer']").val();
    let id = $("#getId").text();
    const args = {
        "answer": answer,
        "id": id
    };

    $.getJSON("/summaryJudge", args, function (data) {
        $("#showPercent").removeAttr('style');
        $("#true-answer").text('正确答案： ' + data.data.standAnswer);
        var interval;
        var percent = 0;
        interval = setInterval(function () {
            $("#count").text(percent)
            $("#water").css("transform", 'translate(0' + ',' + (100 - percent) + '%)');
            if (percent >= data.data.matching) {
                clearInterval(interval);
            }
            percent++;
        }, 40);
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
