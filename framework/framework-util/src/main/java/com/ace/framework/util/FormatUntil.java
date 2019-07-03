package com.ace.framework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author WuZhiWei
 * @since 2016-01-14 17:46
 */
public class FormatUntil {
    /**
     * BigDecimal 去除小数点后面的0
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimalConvert(BigDecimal bigDecimal){
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String decimalStr = decimalFormat.format(bigDecimal);
        return new BigDecimal(decimalStr);
    }

    /**
     * Float 去除小数点后面的0
     *
     * @param proFloat
     * @return
     */
    public static Float floatConvert(Float proFloat){
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        String floatStr = decimalFormat.format(proFloat);
        return new Float(floatStr);
    }
    /**
     * Float 去除小数点后面的0
     *
     * @param proDouble
     * @return
     */
    public static String doubleConvert(double proDouble){
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        String doubleStr = decimalFormat.format(proDouble);

        return doubleStr;
    }
}
