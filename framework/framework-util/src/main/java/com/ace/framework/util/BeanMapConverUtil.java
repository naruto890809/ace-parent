package com.ace.framework.util;

import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * bean map转换工具类
 * @author WuZhiWei
 * @since 2015-12-10 16:14:09
 */
public class BeanMapConverUtil {
    //允许做转换的类型列表
    private static Set<String> legalPropertyTypeSet = new HashSet<String>();
    //基础类型对应的封装类型映射
    private static Map<String, String> basicTypeMapping = new HashMap<String, String>();

    private static Logger logger = Logger.getLogger(BeanMapConverUtil.class);



    static {
        legalPropertyTypeSet.add("byte");
        legalPropertyTypeSet.add("short");
        legalPropertyTypeSet.add("int");
        legalPropertyTypeSet.add("long");
        legalPropertyTypeSet.add("char");
        legalPropertyTypeSet.add("boolean");
        legalPropertyTypeSet.add("float");
        legalPropertyTypeSet.add("double");
        legalPropertyTypeSet.add("java.lang.Byte");
        legalPropertyTypeSet.add("java.lang.Short");
        legalPropertyTypeSet.add("java.lang.Integer");
        legalPropertyTypeSet.add("java.lang.Long");
        legalPropertyTypeSet.add("java.lang.Character");
        legalPropertyTypeSet.add("java.lang.Boolean");
        legalPropertyTypeSet.add("java.lang.Float");
        legalPropertyTypeSet.add("java.lang.Double");
        legalPropertyTypeSet.add("java.lang.String");
        legalPropertyTypeSet.add("java.util.Date");


        basicTypeMapping.put("byte", "java.lang.Byte");
        basicTypeMapping.put("short", "java.lang.Short");
        basicTypeMapping.put("int", "java.lang.Integer");
        basicTypeMapping.put("long", "java.lang.Long");
        basicTypeMapping.put("boolean", "java.lang.Boolean");
        basicTypeMapping.put("float", "java.lang.Float");
        basicTypeMapping.put("double", "java.lang.Double");
    }


    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return returnMap  转化出来的 Map 对象
     */
    public static <T> Map<String, String> beanToMap(T bean) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Class<? extends Object> type = bean.getClass();
        Map<String, String> returnMap = new HashMap<String, String>();
        String propertyName = "";
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                propertyName = descriptor.getName();
                Class<? extends Object> propertyType = descriptor.getPropertyType();
                if (!propertyName.equals("class") && legalPropertyTypeSet.contains(propertyType.getName())) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result == null) {
                        returnMap.put(propertyName, null);
                    } else if (result instanceof Date) {
                        returnMap.put(propertyName, sf.format(result));
                    } else if (result instanceof String) {
                        returnMap.put(propertyName, result.toString());
                    } else {
                        returnMap.put(propertyName, String.valueOf(result));
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Can't get value from the property (" + propertyName + ") on the relevant class(" + bean.getClass().getName() + ")", e);
            return returnMap;
        }
        return returnMap;
    }

    /**
     * 将一个Map对象转化为一个JavaBean
     *
     * @param paramMap 包含属性值的map
     * @param clazz    要转化的类型
     * @return beanObj    转化出来的JavaBean对象
     */
    public static <T> T mapToBean(Map<String, String> paramMap, Class<T> clazz) {
        T beanObj = null;
        if (paramMap == null || paramMap.size() == 0)
            return null;
        String propertyName = null;
        Object propertyValue = null;
        try {
            beanObj = clazz.newInstance();

            for (Map.Entry<String, String> entity : paramMap.entrySet()) {
                propertyName = entity.getKey();
                propertyValue = entity.getValue();
                setProperties(beanObj, propertyName, (String) propertyValue);
            }
        } catch (Exception e) {
            logger.error("Can't set value to the property (" + propertyName + ") with the value(" + propertyValue + ") on the relevant class(" + clazz.getName() + ")");
            return null;
        }
        return beanObj;
    }

    public static <T> void setProperties(T entity, String propertyName,
                                         String value) {
        Method methodSet = null;
        Object target = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, entity.getClass());
            methodSet = pd.getWriteMethod();
            Class<? extends Object> propertyType = pd.getPropertyType();
            String propertyTypeName = propertyType.getName();
            if (legalPropertyTypeSet.contains(propertyTypeName) && value != null) {
                if (propertyTypeName.startsWith("java")) {
                    if (propertyTypeName.equals("java.util.Date")) {
                        target = sf.parse(value);
                    } else if (propertyTypeName.equals("java.lang.String")) {
                        target = value;
                    } else {
                        Method valueMethod = propertyType.getMethod("valueOf", String.class);
                        target = valueMethod.invoke(propertyType, value);
                    }
                } else {
                    if (propertyTypeName.equals("char"))
                        target = Character.valueOf(value.charAt(0));
                    else {
                        String clzName = basicTypeMapping.get(propertyTypeName);
                        if (clzName == null)
                            throw new RuntimeException("not support this basic type!");
                        Class<? extends Object> clzType = Class.forName(clzName);
                        Method valueOf = clzType.getMethod("valueOf", String.class);
                        target = valueOf.invoke(clzType, value);
                    }
                }
                methodSet.invoke(entity, target);
            }

        } catch (Exception e) {
            logger.error("Can't set value to the property (" + propertyName + ") with the value(" + value + ") on the relevant class(" + entity.getClass().getName() + ")");
        }
    }

}
