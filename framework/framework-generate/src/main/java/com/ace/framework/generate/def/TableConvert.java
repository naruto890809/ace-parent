package com.ace.framework.generate.def;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:25
 */
public class TableConvert {
    public static String getNullAble(String nullable) {
        if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable))) {
            return "Y";
        }
        if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable))) {
            return "N";
        }
        return null;
    }
}