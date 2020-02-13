package com.hs.course;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hs.course.utils.HttpClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class CourseApplicationTests {

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
    void text(){
        System.out.println(new Date());
    }

}
