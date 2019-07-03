package com.ace.framework.util.common;

import com.ace.framework.util.redis.MyjJedisCommend;

/**
 * @author WuZhiWei
 * @since 2016-05-12 10:22
 */
public class ReadStatusUtil {


    /**
     * 增加需要统计已读未读的资源 保存keepDays天 超过都算已读
     * @param resourceType
     * @param resourceId
     * @param keepDays
     * @param jedis
     */
    public static void addResource(String resourceType,String resourceId,int keepDays,MyjJedisCommend jedis){
        String key = resourceType+":"+resourceId;
        jedis.sadd(key,"");
        jedis.expire(key,86400*keepDays);
    }

    /**
     * 增加需要统计已读未读的资源 保存10天 超过都算已读
     * @param resourceType
     * @param resourceId
     * @param jedis
     */
    public static void addResource(String resourceType,String resourceId,MyjJedisCommend jedis){
        addResource(resourceType, resourceId, 10, jedis);
    }

    /**
     * 删除需要统计已读未读的资源
     * @param resourceType
     * @param resourceId
     * @param jedis
     */
    public static void removeResource(String resourceType,String resourceId,MyjJedisCommend jedis){
        jedis.del(resourceType+":"+resourceId);
    }

    /**
     * 设置该accountId对该资源已读
     * @param resourceType
     * @param resourceId
     * @param accountId
     * @param jedis
     */
    public static void read(String resourceType,String resourceId,String accountId,MyjJedisCommend jedis){
        String key = resourceType+":"+resourceId;
        if(!jedis.exists(key)){
            return;
        }
        jedis.sadd(key,accountId);
    }

    /**
     * 资源过期 或者设置过已读则返回true 表示已读
     * @param resourceType
     * @param resourceId
     * @param accountId
     * @param jedis
     * @return
     */
    public static boolean hasRead(String resourceType,String resourceId,String accountId,MyjJedisCommend jedis){
        String key = resourceType+":"+resourceId;
        //key已过期  或者accountId存在  则表示已读
        if(!jedis.exists(key)||jedis.sismember(key,accountId)){
            return true;
        }
        return false;
    }
}
