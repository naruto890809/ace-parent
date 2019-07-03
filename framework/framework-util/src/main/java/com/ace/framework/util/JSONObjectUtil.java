package com.ace.framework.util;

import net.sf.json.JSONObject;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * @author WuZhiWei
 * @since 2015-12-18 15:43:00
 */
public class JSONObjectUtil {

    public static<T> T json2Bean(JSONObject json,Class<T> clazz){
        T t = null;
        try{
            t = clazz.newInstance();
            Iterator it = json.keys();
            while (it.hasNext()) {
                String name = "";
                try {
                    name = it.next() + "";
                    String val = json.getString(name);
                    PropertyDescriptor pd = new PropertyDescriptor(name.substring(0, 1).toLowerCase() + name.substring(1), clazz);
                    Class type = pd.getPropertyType();
                    if (type.equals(String.class)) {
                        pd.getWriteMethod().invoke(t, val);
                    } else if (type.equals(Integer.class)) {
                        pd.getWriteMethod().invoke(t, Integer.parseInt(val));
                    } else if (type.equals(Long.class)) {
                        pd.getWriteMethod().invoke(t, Long.parseLong(val));
                    } else if (type.equals(Boolean.class)) {
                        pd.getWriteMethod().invoke(t, "true".equals(val) || "1".equals(val));
                    } else if (type.equals(BigDecimal.class)) {
                        pd.getWriteMethod().invoke(t, new BigDecimal(val));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }catch (Exception e){

        }
        return t;
    }


}
