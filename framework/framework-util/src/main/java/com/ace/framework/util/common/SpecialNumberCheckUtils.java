package com.ace.framework.util.common;

import com.opensymphony.xwork2.util.PatternMatcher;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WuZhiWei
 * @since 2015-12-09 10:21:00
 */
public class SpecialNumberCheckUtils {

    private static List<String> levitPatterns;

    private static Pattern pattern = null;

    static synchronized private void init() {
        if (levitPatterns == null) {
            levitPatterns = new ArrayList<String>();
        } else {
            return;
        }
        // 手机号、生日号、跟公司业务相关的号码
        levitPatterns.add("^(0|13|15|18|168|400|800)[0-9]*$");
        levitPatterns.add("^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$");
        levitPatterns.add("^\\d*(1688|2688|2088|2008|5188|10010|10001|666|888|668|686|688|866|868|886|999)\\d*$");
        // 重复号码，镜子号码
        levitPatterns.add("^(<a>\\d)(\\d)(\\d)\\1\\2\\3$");
        levitPatterns.add("^(\\d)(\\d)(\\d)\\3\\2\\1$");
        // AABB
        levitPatterns.add("^\\d*(\\d)\\1(\\d)\\2\\d*$");
        // AAABBB
        levitPatterns.add("^\\d*(\\d)\\1\\1(\\d)\\2\\2\\d*$");
        // ABABAB
        levitPatterns.add("^(\\d)(\\d)\\1\\2\\1\\2\\1\\2$");
        // ABCABC
        levitPatterns.add("^(\\d)(\\d)(\\d)\\1\\2\\3$");
        // ABBABB
        levitPatterns.add("^(\\d)(\\d)\\2\\1\\2\\2$");
        // AABAAB
        levitPatterns.add("^(\\d)\\1(\\d)\\1\\1\\2$");

        // 4-8 位置重复
        levitPatterns.add("^\\d*(\\d)\\1{2,}\\d*$");
        // 4位以上 位递增或者递减（7890也是递增）
        levitPatterns.add("(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=0)){2,}|(?:0(?=9)|9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2,})\\d");

        // 不能以 518 、918 结尾
        levitPatterns.add("^[0-9]*(518|918)$");
    }

    public static boolean isSpecialNum(String input) {
        Assert.notNull(input);

        for (String levitPattern : levitPatterns) {
            try {
                Pattern pattern = Pattern.compile(levitPattern);
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    static {
        init();
    }
}
