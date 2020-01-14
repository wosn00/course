$(function () {
    $("#choice-btn").click(function () {

        let formData = $("form").serialize();
        $.post("/updateChoice",formData,function (data) {
            if (data.code === 1){
                $.message('修改成功')
            }

        })
    });
    $("#summary-btn").click(function () {

        let formData = $("form").serialize();
        $.post("/updateSummary",formData,function (data) {
            if (data.code === 1){
                $.message('修改成功')
            }

        })
    });

});
