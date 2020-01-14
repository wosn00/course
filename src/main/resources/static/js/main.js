$(function () {

    $(".function1").mouseenter(function () {
        $(this).attr("style", "background-color: #00FFFF; border-radius: 30px");
    });
    $(".function1").mouseleave(function () {
        $(this).removeAttr("style", "background-color: #00FFFF; border-radius: 30px");
    });
    $(".jizuPractice").click(function () {
        window.location.href = "/jizu_chapter";
    });
    $(".jizuExam").click(function () {
        window.location.href = "/exam?course=1";
    });


});