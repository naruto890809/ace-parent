package com.ace.framework.util;

import java.util.ResourceBundle;

/**
 * 配置文件工具类工具类
 *
 * @author WuZhiWei
 * @since 2015-11-10 17:57:17
 */
public class PropertiesUtil {

    /**
     * 获取env配置文件中的某个key的value
     */
    public static String getEnv(String key) {
        return getPropertiesVal("env", key);
    }

    /**
     * 获取config配置文件中的某个key的value
     */
    public static String getConfig(String key) {
        return getPropertiesVal("config", key);
    }

    /**
     * 获取某个配置文件中的某个key的value
     */
    private static String getPropertiesVal(String propertiesName, String key) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(propertiesName);
            return resource.getString(key);
        } catch (Exception e) {
            return null;
        }
    }
}
