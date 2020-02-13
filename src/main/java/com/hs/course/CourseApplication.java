package com.hs.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@MapperScan({"com.hs.course.dao","com.hs.course.daogenerator"})
public class CourseApplication {

    public static void main(String[] args) {
        //设置时区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(CourseApplication.class, args);
    }

}
