package com.hs.course;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;

@SpringBootTest
class CourseApplicationTests {

    @Test
    void contextLoads() {
        DecimalFormat df=new DecimalFormat("0.00");
        String format = df.format((float) 123 / 1233);
        Float aFloat = Float.valueOf(format)*100;
        int i = aFloat.intValue();

        System.out.println(i);
        System.out.println(aFloat);
    }

}
