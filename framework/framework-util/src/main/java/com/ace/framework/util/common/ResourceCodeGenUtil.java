package com.ace.framework.util.common;

import com.ace.framework.util.DateUtil;
import com.ace.framework.util.redis.MyjJedisCommend;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成唯一编号工具类，如：1601150000001
 * @author WuZhiWei
 * @since 2016-01-15 10:50:27
 */
public class ResourceCodeGenUtil {
    public static final int MAX_CODE_SUFFIX = 9999999;
    public static final String CODE_KEY_PREFIX = "codeKey_";//订单号redis key的固定前缀

    public static String genCode(MyjJedisCommend jedis){
        String key = genCodeRedisKey();
        Long value = jedis.incr(key);
        int expireSeconds = DateUtil.getEndOfDaySeconds();
        jedis.expire(key, expireSeconds);
        if (value.intValue() >= MAX_CODE_SUFFIX) {
            throw new RuntimeException("Code number overflow!");
        }

        String codeSuffix = NumberUtil.digitsFormat(value, 7);
        String codePrefix = genCodePrefix();
        return codePrefix + codeSuffix;
    }
    /**
     * 这个方法生成redis中存储订单编号信息的key
     */
    public static String genCodeRedisKey() {
        return CODE_KEY_PREFIX + genCodePrefix();
    }

    /**
     * 这个方法生成订单的前缀，前缀为当前日期按yyMMdd格式化之后的字符串
     */
    public static String genCodePrefix() {
        Date current = new Date();
        SimpleDateFormat codePrefixFormat = new SimpleDateFormat(DateUtil.DATE_PATTERN_yyMMdd);
        return codePrefixFormat.format(current);
    }

}
