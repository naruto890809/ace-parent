package com.ace.framework.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WuZhiWei
 * @since 2016-01-22 15:53
 */
public class UrlUtils {

    public static String changeURLArg(String url,String paramName,String value){
        String replaceText = paramName + "=" + value;
        Pattern pattern = Pattern.compile(paramName + "=[^&]*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return url.replaceAll(paramName + "=[^&]*", replaceText);
        } else {
            if (url.contains("?")) {
                if(url.endsWith("?")){
                    return url+ replaceText;
                }
                return url + '&' + replaceText;
            } else {
                return url + '?' + replaceText;
            }
        }
    }
}
