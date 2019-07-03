package com.ace.framework.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author WuZhiWei
 * @since 2015-12-18 15:43:00
 */
public class XmlUtil {

    public static Document stream2xml(InputStream in){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document string2xml(String xmlStr){
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            return document;
        }catch (Exception e){
            return null;
        }
    }

    public static String map2xml(Map<String, ?> map, String rootName){
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(rootName);
        for(Map.Entry<String,?> entry : map.entrySet()){
            Element element = root.addElement(entry.getKey());
            element.setText(entry.getValue()+"");
        }
        return document.asXML();
    }

    public static String bean2xml(Object obj, String rootName){
        BeanInfo beanInfo;
        Map<String,String> map = new HashMap<String, String>();
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd:pds){
                Object value = pd.getReadMethod().invoke(obj);
                if(value==null){
                    continue;
                }
                map.put(pd.getName(), value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map2xml(map, rootName);
    }

    public static<T> List<T> file2List(File xml,Class<T> clazz){
        List<T> list = new ArrayList<T>();
        try {
            Document document = new SAXReader().read(xml);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for(Element element:elements){
                list.add(element2bean(element,clazz));
            }
        }catch (Exception e){

        }
        return list;
    }

    public static<T> List<T> xml2List(String xml,Class<T> clazz){
        List<T> list = new ArrayList<T>();
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for(Element element:elements){
                list.add(element2bean(element,clazz));
            }
        }catch (Exception e){

        }
        return list;
    }

    public static<T> List<T> xml2List(Document document,Class<T> clazz){
        List<T> list = new ArrayList<T>();
        try {
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for(Element element:elements){
                list.add(element2bean(element,clazz));
            }
        }catch (Exception e){

        }
        return list;
    }

    public static<T> T xml2bean(InputStream xml,Class<T> clazz){
        T t = null;
        try {
            Document document = new SAXReader().read(xml);
            Element root = document.getRootElement();
            t = element2bean(root, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }


    public static<T> T xml2bean(String xml,Class<T> clazz){
        T t = null;
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            t = element2bean(root, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static<T> T xml2bean(Document document,Class<T> clazz){
        T t = null;
        try {
            Element root = document.getRootElement();
            t = element2bean(root, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    private static <T> T element2bean(Element root, Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
            List<Element> list = root.elements();
            for (Element element : list) {
                String name = "";
                try {
                    name = element.getName();
                    name = name.substring(0,1).toLowerCase()+name.substring(1);
                    PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
                   /* Field field = clazz.getDeclaredField(name);
                    Class type = field.getType();*/
                    Class type = pd.getPropertyType();
                    if (type.equals(String.class)) {
                        pd.getWriteMethod().invoke(t, element.getTextTrim());
                    } else if (type.equals(Integer.class)) {
                        pd.getWriteMethod().invoke(t, Integer.parseInt(element.getTextTrim()));
                    } else if (type.equals(Long.class)) {
                        pd.getWriteMethod().invoke(t, Long.parseLong(element.getTextTrim()));
                    } else if (type.equals(Boolean.class)) {
                        pd.getWriteMethod().invoke(t, "true".equals(element.getTextTrim())||"1".equals(element.getTextTrim()));
                    } else if (type.equals(BigDecimal.class)) {
                        pd.getWriteMethod().invoke(t, new BigDecimal(element.getTextTrim()));
                    } else if(type.equals(Map.class)){
                        pd.getWriteMethod().invoke(t, element2map(element));
                    }
                   /* String setMethodName = "set" + getMethodName(name);
                    if (type.equals(String.class)) {
                        clazz.getMethod(setMethodName, String.class).invoke(t, element.getTextTrim());
                    } else if (type.equals(Integer.class)) {
                        clazz.getMethod(setMethodName, Integer.class).invoke(t, Integer.parseInt(element.getTextTrim()));
                    } else if (type.equals(Long.class)) {
                        clazz.getMethod(setMethodName, Long.class).invoke(t, Long.parseLong(element.getTextTrim()));
                    } else if (type.equals(Boolean.class)) {
                        clazz.getMethod(setMethodName, Boolean.class).invoke(t, "true".equals(element.getTextTrim()));
                    } else if (type.equals(BigDecimal.class)) {
                        clazz.getMethod(setMethodName, BigDecimal.class).invoke(t, new BigDecimal(element.getTextTrim()));
                    } else if(type.equals(Map.class)){
                        clazz.getMethod(setMethodName, Map.class).invoke(t, element2map(element));
                    }*/
                } catch (Exception e) {
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    private static Map<String,String> element2map(Element root){
        Map map = new HashMap();
        List<Element> list = root.elements();
        for (Element element : list) {
            map.put(element.getName(),element.getTextTrim());
        }
        return map;
    }


    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


}
