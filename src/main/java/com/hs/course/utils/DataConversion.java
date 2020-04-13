package com.hs.course.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataConversion {
    /**
     * 除法求百分比整数位
     */
    public static int division(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format((float) a / b);
        Float aFloat = Float.valueOf(format) * 100;
        return aFloat.intValue();

    }

    /**
     * long时间类型转成格式化的Date类型
     */
    public static Date getDate(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(time);
        Date date = null;
        try {
            date = df.parse(format);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    /**
     * 判断是否来自手机端访问
     */
    public static boolean JudgeIsMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = {"iphone", "android", "ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile"};
        if (request.getHeader("User-Agent") != null) {
            String agent = request.getHeader("User-Agent");
            for (String mobileAgent : mobileAgents) {
                if (agent.toLowerCase().indexOf(mobileAgent) >= 0 && agent.toLowerCase().indexOf("windows nt") <= 0 && agent.toLowerCase().indexOf("macintosh") <= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

    /**
     * 获取请求ip
     */
    public static String getAddress(HttpServletRequest request) {
        /**
         * 获取Ip地址
         * @param request
         * @return
         */
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


    public static String getDetailAddress(JSONObject jsonObject) {
        String s = "";
        if (!jsonObject.getString("status").equals("0")) {
            System.out.println("=======================" + jsonObject.getString("status"));
            s = "百度API状态异常";
            return s;
        }
        String province = jsonObject.getJSONObject("content").getJSONObject("address_detail").getString("province");
        String city = jsonObject.getJSONObject("content").getJSONObject("address_detail").getString("city");
        String district = jsonObject.getJSONObject("content").getJSONObject("address_detail").getString("district");
        String street = jsonObject.getJSONObject("content").getJSONObject("address_detail").getString("street");
        String streetNumber = jsonObject.getJSONObject("content").getJSONObject("address_detail").getString("street_number");
        s = province + city + district + street + streetNumber;
        return s;
    }

}
