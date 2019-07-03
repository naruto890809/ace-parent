package com.ace.framework.util.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @author WuZhiWei
 * @since 2016-01-19 15:13
 */
public class ArrayUtil {

    public static String array2String(String [] array){
        if(array==null||array.length==0){
            return null;
        }
        StringBuffer sb=new StringBuffer();
        for(int i =0 ;i<array.length;i++){
            sb.append(array[i]).append(";");
        }
        sb=sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String []  string2Array(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        StringBuffer sb=new StringBuffer();
        return str.split(";");
    }

}
