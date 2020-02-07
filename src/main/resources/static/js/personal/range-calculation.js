$(function () {
    //计组获取已答题数
    const args = {
        "course": 1
    };
    $.getJSON("/get_range", args, function (data) {
        $(".jizu-choice").css("width", data.data.jizuChoiceRange + '%');
        $(".jizu-summary").css("width", data.data.jizuSummaryRange + '%');
        $("#jizu-choice-count").text(' 已完成' + data.data.jizuChoiceAnswered + '/' + data.data.jizuChoiceCount);
        $("#jizu-summary-count").text(' 已完成' + data.data.jizuSummaryAnswered + '/' + data.data.jizuSummaryCount);
        setTimeout(function () {
            $(".jizu-choice").find("span").text(data.data.jizuChoiceRange + '%');
            $(".jizu-summary").find("span").text(data.data.jizuSummaryRange + '%');
        }, 500);

    });

    //获取计组，数构正确率
    $.getJSON("/get_accuracy_rate", {}, function (data) {
        //计组部分
        $("#jizu-choice-rate-1").attr("data-percent",data.data.jizuChoiceRate+'%');
        let jizuRate = Math.round(629*data.data.jizuChoiceRate/100);
        $("#jizu-choice-rate-2").attr("stroke-dashoffset",jizuRate);

        $("#jizu-summary-rate-1").attr("data-percent",data.data.jizuSummaryRate+'%');
        let jizuRate2 = Math.round(629*data.data.jizuSummaryRate/100);
        $("#jizu-summary-rate-2").attr("stroke-dashoffset",jizuRate2);

        //数构部分
        $("#shugou-choice-rate-1").attr("data-percent",data.data.shugouChoiceRate+'%');
        let shugouRate = Math.round(629*data.data.shugouChoiceRate/100);
        $("#shugou-choice-rate-2").attr("stroke-dashoffset",shugouRate);

        $("#shugou-summary-rate-1").attr("data-percent",data.data.shugouSummaryRate+'%');
        let shugouRate2 = Math.round(629*data.data.shugouSummaryRate/100);
        $("#shugou-summary-rate-2").attr("stroke-dashoffset",shugouRate2);
    });




    //数构获取已答题数
    $.getJSON("/get_range", {"course":2}, function (data) {
        $(".shugou-choice").css("width", data.data.shugouChoiceRange + '%');
        $(".shugou-summary").css("width", data.data.shugouSummaryRange + '%');
        $("#shugou-choice-count").text(' 已完成' + data.data.shugouChoiceAnswered + '/' + data.data.shugouChoiceCount);
        $("#shugou-summary-count").text(' 已完成' + data.data.shugouSummaryAnswered + '/' + data.data.shugouSummaryCount);
        setTimeout(function () {
            $(".shugou-choice").find("span").text(data.data.shugouChoiceRange + '%');
            $(".shugou-summary").find("span").text(data.data.shugouSummaryRange + '%');
        }, 500);

    });

});