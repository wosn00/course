package com.hs.course.stateEnum;


public enum LoginState {
    HTTP_200("200", "请求成功"),
    //注册
    HTTP_201("201", "密码长度不够！"),
    HTTP_202("202", "验证码错误！"),
    HTTP_203("203", "账号已经存在！"),
    HTTP_204("204", "账号长度不够！"),
    HTTP_205("205","请先获取验证码！"),
    HTTP_208("208","该手机号已被注册！"),
    //登录
    HTTP_206("206","用户名不存在！"),
    HTTP_207("207","密码错误！"),
    //重置密码
    HTTP_209("209","该手机号尚未注册，请前往注册！"),
    HTTP_210("210","该账号名已被注册");

    private String code;
    private String msg;

    LoginState(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
