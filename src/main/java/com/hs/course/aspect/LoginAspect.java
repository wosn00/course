package com.hs.course.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hs.course.daogenerator.RecordLoginGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.RecordLoginGenerator;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.Status;
import com.hs.course.utils.DataConversion;
import com.hs.course.utils.HttpClientUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Aspect
@Component
public class LoginAspect {
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;
    @Autowired
    private RecordLoginGeneratorMapper recordLoginGeneratorMapper;
    @Value("${address-location.key}")
    private String addressKey;
    @Value("${address-location.url}")
    private String url;

    private static Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    @Around("execution(* com.hs.course.controller.LoginController.login(..))")
    public Object loginAspect(ProceedingJoinPoint p) {
        String inputName = null;
        String inputPwd = null;
        String pwd = null;
        String source = null;
        String system = null;
        String browserName = null;
        String ip = null;
        String address = null;
        long now = 0;
        Date date = null;
        Object o;
        try {
            Object[] args = p.getArgs();
            inputName = (String) args[0];
            inputPwd = (String) args[1];
            HttpServletRequest request = (HttpServletRequest) args[3];
            UserGeneratorExample ex = new UserGeneratorExample();
            ex.createCriteria().andNameEqualTo(inputName);
            List<UserGenerator> userGenerators = userGeneratorMapper.selectByExample(ex);
            //真实密码
            pwd = "";
            if (userGenerators.size() != 0) {
                pwd = userGenerators.get(0).getPwd();
            }
            source = "电脑";
            boolean isMoblie = DataConversion.JudgeIsMoblie(request);
            if (isMoblie) {
                source = "手机";
            }
            //获取浏览器信息
            String ua = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(ua);
            Browser browser = userAgent.getBrowser();
            OperatingSystem os = userAgent.getOperatingSystem();
            //系统名称
            system = os.getName();
            //浏览器名称
            browserName = browser.getName();
            //获取请求ip
            ip = DataConversion.getAddress(request);
            HashMap<String, String> map = new HashMap<>();
            map.put("ak", addressKey);
            map.put("ip", ip);
            map.put("coor", "bd09ll");
            address = "";
            try {
                String s = HttpClientUtil.get(url, map);
                JSONObject jsonObject = JSON.parseObject(s);
                address = DataConversion.getDetailAddress(jsonObject);

            } catch (Exception e) {
                address = "Http客户端异常";
                logger.error("Http客户端异常",e);
            }
            now = System.currentTimeMillis();
            date = DataConversion.getDate(now);
        } catch (Exception e) {
            logger.error("登录切面出错拉！！！",e);
        }
        try {
            o = p.proceed();
        } catch (Throwable throwable) {
            logger.error("登录控制器出错！！！",throwable);
            throw new RuntimeException();
        }
        try {
            long next = System.currentTimeMillis();
            long timeConsume = next - now;
            Status status = (Status) o;
            String loginResult = "失败";
            if (status.getCode().equals("200")) {
                loginResult = "成功";
            }
            //存登录记录进数据库
            RecordLoginGenerator re = new RecordLoginGenerator();
            re.setUserName(inputName);
            re.setPwd(inputPwd);
            re.setUserPwd(pwd);
            re.setSource(source);
            re.setBrowser(browserName);
            re.setSystem(system);
            re.setIp(ip);
            re.setLoginResult(loginResult);
            re.setAddress(address);
            re.setTimeConsume((int) timeConsume);
            re.setDate(date);
            recordLoginGeneratorMapper.insertSelective(re);
        } catch (Exception e) {
            logger.error("登录切面出错拉！！！",e);
        }

        return o;
    }

}
