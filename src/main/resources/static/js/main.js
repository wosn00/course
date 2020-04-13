$(function () {

    $(".function1").mouseenter(function () {
        $(this).attr("style", "background-color: #00FFFF; border-radius: 30px");
    });
    $(".function1").mouseleave(function () {
        $(this).removeAttr("style", "background-color: #00FFFF; border-radius: 30px");
    });
    //计组部分跳转
    $(".jizuPractice").click(function () {
        window.location.href = "/jizu_chapter";
    });
    $(".jizuExam").click(function () {
        window.location.href = "/exam?course=1";
    });
    //数构部分跳转
    $(".shugouPractice").click(function () {
        window.location.href = "/shugou_chapter";
    });
    $(".shugouExam").click(function () {
        window.location.href = "/exam?course=2";
    });
    $(".jizuGraduate").click(function () {
        $.message({
            message: 'Not Open Now',
            type: 'error'
        });
    });
    $(".shugouGraduate").click(function () {
        $.message({
            message: 'Not Open Now',
            type: 'error'
        });
    });



});