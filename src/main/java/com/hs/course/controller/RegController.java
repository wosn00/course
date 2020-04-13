package com.hs.course.controller;

import com.hs.course.entity.Status;
import com.hs.course.loginService.PhoneMessageService;
import com.hs.course.loginService.RegJudgeService;
import com.hs.course.loginService.ResetJudgeService;
import com.hs.course.vo.PhoneMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * 注册
 */
@Controller
@RequestMapping("/register")
public class RegController {
    @Autowired
    private PhoneMessageService phoneMessageService;

    @Autowired
    private RegJudgeService regJudgeService;

    @Autowired
    private ResetJudgeService resetJudgeService;
    private static Logger logger = LoggerFactory.getLogger(RegController.class);

    @RequestMapping("/message")
    @ResponseBody
    public PhoneMessage message(String phone, HttpSession session) {
        String v = String.format("%04d", new Random().nextInt(9999));
        session.setAttribute("phoneMessage", v);
        logger.info("正在发送验证码.......，手机号：{},验证码：{}",phone,v);
        return phoneMessageService.sendMessage(phone, v);
    }

    @RequestMapping("/reg")
    @ResponseBody
    public Status reg(String name, String pwd, String phone, String code, HttpSession session) {
        logger.info("正在注册账号......,name:{},pwd:{},phone:{},code:{}",name,pwd,phone,code);
        return regJudgeService.regJudge(name,pwd,phone,code,session);
    }

    @RequestMapping("/reset")
    @ResponseBody
    public Status reset(String name, String pwd, String phone, String code, HttpSession session) {
        logger.info("正在重置密码......,name:{},pwd:{},phone:{},code:{}",name,pwd,phone,code);
        return resetJudgeService.resetJudge(name,pwd,phone,code,session);
    }
}
