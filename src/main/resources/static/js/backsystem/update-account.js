$(function () {
    $("#update-account").click(function () {

        let formData = $("form").serialize();
        $.post("/updateAccount",formData,function (data) {
            if (data.code === 1){
                $.message('修改成功')
            }

        })
    });


});
