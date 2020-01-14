var _handle = '';//储存电话是否填写正确
$(function () {
    //初始化弹窗插件
    Notiflix.Report.Init();
    //边框样式
    $(".signup-form input").on("focus", function () {
        $(this).parent().addClass("border");
    });
    $(".signup-form input").on("blur", function () {
        $(this).parent().removeClass("border");
    });

    //步骤切换
    var _boxCon = $(".box-con");
    $(".move-login").on("click", function () {
        $(_boxCon).css({
            'marginLeft': 0
        })
    });
    $(".move-signup").on("click", function () {
        $(_boxCon).css({
            'marginLeft': -320
        })
    });
    $(".move-other").on("click", function () {
        $(_boxCon).css({
            'marginLeft': -640
        })
    });
    $(".move-reset").on("click", function () {
        $(_boxCon).css({
            'marginLeft': -960
        })
    });
    $(".reset-move-login").on("click", function () {
        $(_boxCon).css({
            'marginLeft': 0
        })
    });
    $("body").on("click", ".move-addinf", function () {
        $(_boxCon).css({
            'marginLeft': -1280
        })
    });


});

//表单验证
function showNotic(_this) {
    $(_this).parents(".form-group").find(".error-notic").fadeIn(100);
    $(_this).focus();
}//错误提示显示
function hideNotic(_this) {
    $(_this).parents(".form-group").find(".error-notic").fadeOut(100);
}//错误提示隐藏
function showMessage(_this) {
    $(_this).parents(".form-group").find(".error-message").fadeIn(100);
    $(_this).focus();
}//短信发送失败显示
function hideMessage(_this) {
    $(_this).parents(".form-group").find(".error-message").fadeOut(100);
}//错误提示隐藏
var verify = {
    verifyMobile: function (_this) {
        hideMessage(_this);
        var validateReg = /^((\+?86)|(\(\+86\)))?1\d{10}$/;
        var _value = $(_this).prev().val();
        if (!validateReg.test(_value)) {
            showNotic(_this);
            _handle = false;
            // return _handle
        } else {
            hideNotic(_this);
            countdown(_this);
            _handle = true;
            var args = {"phone": _value};
            $.getJSON("/register/message", args, function (data) {
                var code = data.code;
                if (code != 0) {
                    showMessage(_this);
                }
            });
        }
        return true;
    },//验证手机号码
    PasswordLenght: function (_this) {
        var _length = $(_this).val().length;
        if (_length < 6) {
            showNotic(_this)
        } else {
            hideNotic(_this)
        }
    }//验证设置密码长度
}

//登录判断
function login() {
    $("#name-msg").text("");
    $("#pwd-msg").text("");
    var name = $("#name").val();
    var pwd = $("#pwd").val();
    var args = {"name": name, "pwd": pwd};
    $.getJSON("/login", args, function (data) {
        if (data.code == 200) {
            window.location.href = "/main";
        } else if (data.code == 206) {
            $("#name-msg").text(data.msg);
        } else if (data.code == 207) {
            $("#pwd-msg").text(data.msg);
        }
    });
}

//注册判断
function reg() {
    Notiflix.Notify.Init();
    Notiflix.Report.Init();
    $("#reg-pwd-msg").text("");
    $("#reg-code-msg").text("");
    $("#reg-name-msg").text("");
    $("#reg-phone-msg").text("");
    var name = $("#reg-name").val();
    var pwd = $("#reg-pwd").val();
    var phone = $("#reg-phone").val();
    var code = $("#reg-code").val();
    var args = {
        "name": name,
        "pwd": pwd,
        "phone": phone,
        "code": code,
    };
    $.getJSON("/register/reg", args, function (data) {
        if (data.code == 201) {
            $("#reg-pwd-msg").text(data.msg);
        } else if (data.code == 202) {
            $("#reg-code-msg").text(data.msg);
        } else if (data.code == 203 || data.code == 204) {
            $("#reg-name-msg").text(data.msg);
        } else if (data.code == 205 || data.code == 208) {
            $("#reg-phone-msg").text(data.msg);
        } else if (data.code == 200) {
            Notiflix.Report.Success( '注册成功', '', 'Click' );
        }
    });


}

//重置密码判断
function reset() {
    $("#reset-pwd-msg").text("");
    $("#reset-code-msg").text("");
    $("#reset-name-msg").text("");
    $("#reset-phone-msg").text("");
    var name = $("#reset-name").val();
    var pwd = $("#reset-pwd").val();
    var phone = $("#reset-phone").val();
    var code = $("#reset-code").val();
    var args = {
        "name": name,
        "pwd": pwd,
        "phone": phone,
        "code": code,
    };
    $.getJSON("/register/reset", args, function (data) {
        if (data.code == 201) {
            $("#reset-pwd-msg").text(data.msg);
        } else if (data.code == 202) {
            $("#reset-code-msg").text(data.msg);
        } else if (data.code == 204 || data.code == 210) {
            $("#reset-name-msg").text(data.msg);
        } else if (data.code == 205 || data.code == 209) {
            $("#reset-phone-msg").text(data.msg);
        } else if (data.code == 200) {
            Notiflix.Report.Success( '注册成功', '', 'Click' );
        }
    });

}
