package com.hs.course.courseService;

import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 答案匹配度算法
 */
@Service
public class AnswerMatching {

    /**
     * @param a           回答
     * @param standAnswer 标准答案
     * @return 匹配度
     */
    public Float matching(String a, String standAnswer) {
        //去掉a中的(1)和（1）
        Pattern p = Pattern.compile("\\(\\d\\)");
        Matcher m = p.matcher(a);
        String s2 = m.replaceAll("");
        Pattern p2 = Pattern.compile("（\\d）");
        Matcher m2 = p2.matcher(s2);
        String s1 = m2.replaceAll("");
        //通过正则表达式提取中文和数字
        Pattern p1 = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]");
        Matcher m1 = p1.matcher(s1);
        StringBuilder sb=new StringBuilder();
        while (m1.find()){
            sb.append(m1.group());
        }
        String answer =sb.toString();
        System.out.println(answer+"======");
        //去掉多余的符号
        String s = standAnswer.trim().replace(" ", "").replace("，", "")
                .replace("。", "").replace(",", "").replace("：", "")
                .replace(";","；");
        String[] split = s.split("[；,，。]");
        int tag = 0;
        for (String temp : split) {
            if (answer.contains(temp)) {
                tag++;
            }
        }
        //关键词命中匹配度
        float result = (float) tag / split.length;
        //字符串相似匹配度
        Float result2 = levenshtein(answer, s.replace("；",""));
        if (result > result2) {
            return result;
        } else {
            return result2;
        }
    }


    /**
     * 　中文匹配度算法
     */
    public static Float levenshtein(String str1, String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        //计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        return similarity;
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

}
