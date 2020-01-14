package com.hs.course.controller;

import com.hs.course.entity.Status;
import com.hs.course.loginService.PhoneMessageService;
import com.hs.course.loginService.RegJudgeService;
import com.hs.course.loginService.ResetJudgeService;
import com.hs.course.vo.PhoneMessage;
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

    @RequestMapping("/message")
    @ResponseBody
    public PhoneMessage message(String phone, HttpSession session) {
        String v = String.format("%04d", new Random().nextInt(9999));
        session.setAttribute("phoneMessage", v);
        return phoneMessageService.sendMessage(phone, v);
    }

    @RequestMapping("/reg")
    @ResponseBody
    public Status reg(String name, String pwd, String phone, String code, HttpSession session) {
       return regJudgeService.regJudge(name,pwd,phone,code,session);
    }

    @RequestMapping("/reset")
    @ResponseBody
    public Status reset(String name, String pwd, String phone, String code, HttpSession session) {
        return resetJudgeService.resetJudge(name,pwd,phone,code,session);
    }
}
