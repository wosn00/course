package com.hs.course;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@MapperScan({"com.hs.course.dao","com.hs.course.daogenerator"})
public class CourseApplication {
    private static Logger logger = LoggerFactory.getLogger(CourseApplication.class);

    public static void main(String[] args) {
        //设置时区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(CourseApplication.class, args);
        logger.info("---------------------------------------系统启动成功!------------------------------------------");


    }

}
