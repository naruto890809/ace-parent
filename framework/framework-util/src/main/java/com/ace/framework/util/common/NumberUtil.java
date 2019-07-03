package com.ace.framework.util.common;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author WuZhiWei
 * @since 2015-12-18 16:53:00
 */
public class NumberUtil {

    public static int num2NewNum(int num) {
        int newNum = 0;
        int temp = 1;
        while (num > 0) {
            if (num % 10 != 0) {
                break;
            }
            newNum++;
            num = num / 10;
        }
        while (num > 0) {
            int n = num % 10 + 4;
            newNum = newNum * 10 + n;
            num = num /10;
        }
        newNum = newNum<<temp-temp;
        return newNum;
    }

    public static int getRandom(int min,int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    /**
     * 这个方法把一个long类型的数值格式化，格式化要求包括不使用组，
     * 位数为给定的参数，多余的位数强制截断，不足的位数补零
     *
     * @param number 需要格式化的数值
     * @param length 格式化后的位数
     * @since  2016-01-15 10:11:35 WuZhiWei
     */
    public static String digitsFormat(long number, int length) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(length);
        numberFormat.setMinimumIntegerDigits(length);
        return numberFormat.format(number);
    }

    /**
     * 这个方法将传入的大实数按标度为2，舍入模式为四舍五入处理并返回处理后的十进制数
     *
     * @param bigDecimal 需要处理的实数
     */
    public static BigDecimal scale(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * 保留俩位小数，小数点后面是0 清除
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimalConvert(BigDecimal bigDecimal){
        return bigDecimalConvert(bigDecimal,"0.##");
    }

    /**
     * 保留pattern，小数点后面是0 清除
     *
     * @param bigDecimal
     * @param pattern
     * @return
     */
    public static BigDecimal bigDecimalConvert(BigDecimal bigDecimal,String pattern){
        DecimalFormat decimalFormat=null;
        if(StringUtils.isBlank(pattern)){
            decimalFormat = new DecimalFormat("0.##");
        }
        decimalFormat = new DecimalFormat(pattern);
        String decimalStr = decimalFormat.format(bigDecimal);
        return new BigDecimal(decimalStr);
    }
}