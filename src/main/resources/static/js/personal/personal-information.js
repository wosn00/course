$(function () {
    const args = {};
    $.getJSON("/get_information", args, function (data) {
        $("input[name='name']").val(data.data.name);
        $("input[name='className']").val(data.data.className);
        $("input[name='studentID']").val(data.data.studentID);
        $("input[name='phone']").val(data.data.phone);
        $("input[name='date']").val(data.data.date);
    });


    $("#update-information").click(function () {
        let name = $("input[name='name']").val();
        let className = $("input[name='className']").val();
        let studenyID = $("input[name='studentID']").val();
        const args = {
            "name": name,
            "className": className,
            "studentID": studenyID
        };
        $.getJSON("/update_information", args, function (data) {
            if (data.code === 1) {
                $.message('更新成功')
            } else {
                $.message({
                    message: '更新失败',
                    type: 'error'
                });
            }

        });

    });
});