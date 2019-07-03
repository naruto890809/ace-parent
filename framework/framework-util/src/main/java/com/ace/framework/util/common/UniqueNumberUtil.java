package com.ace.framework.util.common;

import com.ace.framework.util.redis.MyjJedisCommend;
import org.apache.commons.lang.StringUtils;

/**
 * @author WuZhiWei
 * @since 2015-12-01 16:09:00
 */
public class UniqueNumberUtil {

    private static int count;

    private static final String DEFAULT_REDIS_KEY = "ace_unique_number";

    /**
     * 生成唯一数字 5位起
     * @param jedis
     * @param firstNumOdd  第一位是否为基数  不填为不限制
     * @return
     */
    public static String getNumberStr(MyjJedisCommend jedis,Boolean firstNumOdd){
       return getNumberStr(jedis,firstNumOdd,DEFAULT_REDIS_KEY);
    }

    public static String getNumberStr(MyjJedisCommend jedis,String redisKey){
        return getNumberStr(jedis,null,redisKey);
    }

    public static String getNumberStr(MyjJedisCommend jedis,Boolean firstNumOdd,String redisKey){
        if(StringUtils.isBlank(redisKey)){
            redisKey = DEFAULT_REDIS_KEY;
        }
        String num = jedis.incr(redisKey)+"";
        int pre = 0;
        if(firstNumOdd==null){
            pre = (int)(Math.random()*10);
        }else{
            pre = ((int)(Math.random()*5))*2;
            if(firstNumOdd){
                pre++;
            }
        }
        String str = pre+StringUtil.getRandomNumberStr(3)+num;
        if(SpecialNumberCheckUtils.isSpecialNum(str)){
            //为靓号重新生成
            return getNumberStr(jedis,firstNumOdd);
        }
        return str;
    }

    public static String getNumberStr(MyjJedisCommend jedis){
        return getNumberStr(jedis,null,null);
    }

}
