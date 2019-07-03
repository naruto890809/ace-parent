package com.ace.framework.util;


import java.util.UUID;

/**
 * uuid工具类
 *
 * @author WuZhiWei
 * @since 2015-11-10 11:22:21
 */
public class UUIDUtil {

    public static String genUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
