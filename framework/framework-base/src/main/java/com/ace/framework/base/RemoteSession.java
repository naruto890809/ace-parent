package com.ace.framework.base;

import com.ace.framework.util.SerializeUtil;
import com.ace.framework.util.redis.MyjJedisCommend;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuZhiWei
 * @since 2015-11-23 10:31:00
 */
public class RemoteSession extends ConcurrentHashMap<String,Object> implements Serializable {

    private static final long serialVersionUID = 8403969492995644866L;

    public static final int EXPIRE_SECONDS = 1800;

    private String remoteSessionId;

//    @Resource
    private MyjJedisCommend jedis;

    public MyjJedisCommend getJedis() {
        return jedis;
    }

    public void setJedis(MyjJedisCommend jedis) {
        this.jedis = jedis;
    }

    public RemoteSession(String remoteSessionId) {
        this.remoteSessionId = remoteSessionId;
    }

    public<T extends  Serializable> T getAttribute(String key){
        byte[] byte_key = getKey(key);
        byte[] byte_entity = jedis.get(byte_key);
        if(byte_entity==null||byte_entity.length==0){
            return null;
        }
        jedis.expire(byte_key, EXPIRE_SECONDS);
        return SerializeUtil.deserialize(byte_entity);
    }

    public void setAttribute(String key,Serializable serializable){
        byte[] byte_key = getKey(key);
        jedis.set(byte_key, SerializeUtil.serialize(serializable));
        jedis.expire(byte_key, EXPIRE_SECONDS);
        super.put(key,serializable);
    }


    public void removeAttribute(String key){
        jedis.del(getKey(key));
        super.remove(key);
    }

    private byte[] getKey(String key){
        return (remoteSessionId+";;"+key).getBytes();
    }

    @Override
    public Object get(Object key) {
        Object value = super.get(key);
        if(value!=null){
            return value;
        }
        return this.getAttribute((String)key);
    }

    @Override
    public boolean containsKey(Object key) {
        return jedis.get(getKey((String)key))!=null;
    }

    @Override
    public Object put(String key, Object value) {
        this.setAttribute(key,(Serializable)value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        for(String key:m.keySet()){
            this.setAttribute(key,(Serializable)m.get(key));
        }
    }

    @Override
    public Object remove(Object key) {
        this.removeAttribute((String)key);
        return super.remove(key);
    }
}
