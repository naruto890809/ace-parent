package com.ace.framework.util;

import com.ace.framework.util.encryption.DesUtil;

/**
 * @author WuZhiWei
 * @since 2015-11-20 14:52
 */
public class DesUtilTest {
    public static void main(String[] args) {
        //1C71EB698D3AFD4A1C71EB698D3AFD4A2926DA204198349E
        //B41A0CDBA57E42F15AA44A8FAF280F545AA44A8FAF280F545AA44A8FAF280F545AA44A8FAF280F545AA44A8FAF280F54
        String s=DesUtil.strEnc("000","bd5b13fab5e949748f7020d617fc8761","wzw","1448435785000");
//        System.out.println(System.currentTimeMillis());

        System.out.println(s);
        System.out.println(DesUtil.strDec("7B9CE3C6DC2FD944","bd5b13fab5e949748f7020d617fc8761","wzw","1448435785000"));
    }
}
