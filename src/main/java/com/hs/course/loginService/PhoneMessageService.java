package com.hs.course.loginService;

import com.alibaba.fastjson.JSON;
import com.hs.course.vo.PhoneMessage;
import com.zhenzi.sms.ZhenziSmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *  手机
 */

@Service
public class PhoneMessageService {
    @Value("${message.apiUrl}")
    private String apiUrl;

    @Value("${message.appId}")
    private String appId;

    @Value("${message.appSecret}")
    private String appSecret;
    private static Logger logger = LoggerFactory.getLogger(PhoneMessageService.class);

    /**
     *
     * @param phone 手机号
     * @param v 啦啦啦
     * @return 返回phoneMessage
     */

    public PhoneMessage sendMessage(String phone, String v) {
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        String result = null;
        try {
            result = client.send(phone, "验证码为"+v+"，请在120秒内输入。");
            logger.info("发送成功！");
        } catch (Exception e) {
            logger.error("短信发送失败，API出现异常",e);
            PhoneMessage phoneMessage = new PhoneMessage("2", "wrong!!!");
            return phoneMessage;
        }
        PhoneMessage o = JSON.parseObject(result, PhoneMessage.class);
        return o;
    }
}
