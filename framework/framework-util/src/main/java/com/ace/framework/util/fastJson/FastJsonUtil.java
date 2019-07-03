package com.ace.framework.util.fastJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2016-03-12 10:22
 */
public class FastJsonUtil {
   public static String obj2Json(Object obj,Map<Class<?>, String[]> excludes) {
       if(excludes==null){
           return JSON.toJSONString(obj);
       }
       ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter();
       filter.setExcludes(excludes);
       return JSON.toJSONString(obj,filter, SerializerFeature.WriteMapNullValue);
   }
}
