package com.ace.framework.util.common;

/**
 * @author WuZhiWei
 * @since 2015-12-01 15:45:00
 */
public class PhoneUtil {

    public static String fillStar(String phone){
        return phone.substring(0,3) + "****" + phone.substring(7);
    }
}
