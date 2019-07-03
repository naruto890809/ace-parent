package com.ace.framework.util;

import java.io.*;

/**
 * 对象序列化和反序列化工具类
 * @author WuZhiWei
 * @since 2015-11-17 10:38:00
 */
public class SerializeUtil {

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Serializable object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {

        }
        return new byte[0];
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static<T extends  Serializable> T deserialize(byte[] bytes) {
        if(bytes==null){
            return null;
        }
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            return (T)obj;
        } catch (Exception e) {

        }
        return null;
    }
}
