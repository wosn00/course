package com.hs.course;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hs.course.courseService.AccuracyRateService;
import com.hs.course.entity.Choice;
import com.hs.course.entity.User;
import com.hs.course.utils.HttpClientUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
class CourseApplicationTests {
    public static Logger logger = LoggerFactory.getLogger(CourseApplicationTests.class);

    @Test
    void contextLoads() {
        //http://api.map.baidu.com/location/ip?ak=nXl4A0VpQFRgXRuqv9xMrTZqnsMc8lcr&ip=59.59.21.236&coor=bd09ll
        HashMap<String, String> map = new HashMap<>();
        map.put("ak","nXl4A0VpQFRgXRuqv9xMrTZqnsMc8lcr");
        map.put("ip","59.59.21.236");
        map.put("coor","bd09ll");
        try {
            String s = HttpClientUtil.get("http://api.map.baidu.com/location/ip", map);
            JSONObject jsonObject = JSON.parseObject(s);
            System.out.println(jsonObject.get("address"));
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test(){
        User user = new User();
        user.setClassName("一班");
        user.setRole(2);
        user.setId(12);
//        Exception exception = new Exception("异常例子");
        logger.info("用户信息：{}",user);
        logger.info("打印日志测试。。。。。。。。。。。。{}。",233);
        logger.info("打印日志测试。。。。。。。。。。{}。。。","占位符");
//        logger.error("异常例子:",exception);
        logger.info("打印日志测试。。。。。。。。。。。。。");
        logger.info("打印日志测试。。。。。。。。。。。。。");
        logger.error("出错啦啦啦啦啦啦啦啦啦。。。。。");

    }


}
