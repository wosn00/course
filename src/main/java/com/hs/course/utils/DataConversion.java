package com.hs.course.utils;

import java.text.DecimalFormat;

public class DataConversion {
    /**
     * 除法求百分比整数位
     */
    public static int division(int a,int b){
        if (a ==0 || b ==0){
            return 0;
        }
        DecimalFormat df=new DecimalFormat("0.00");
        String format = df.format((float) a / b);
        Float aFloat = Float.valueOf(format)*100;
        return aFloat.intValue();

    }
}
