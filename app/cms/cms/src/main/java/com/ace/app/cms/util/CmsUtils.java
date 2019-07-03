package com.ace.app.cms.util;

import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.Page;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.common.NumberUtil;
import com.ace.framework.util.redis.MyjJedisCommend;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @author WuZhiWei
 * @since 2015-11-19 10:19:21
 */
public class CmsUtils {
    /**
     * 分页对象的校验（是否为空，是否自动分页，是否设置合理的首页）
     *
     * @param page 需要校验的分页对象
     */
    public static void validPage(Page<?> page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is null!");
        }

        if (page.isAutoPaging()) {
            int pageSize = page.getPageSize();
            if (pageSize < 1) {
                throw new IllegalArgumentException("Page size must bigger than 1!");
            }

            int first = page.getFirst();
            if (first < 1) {
                throw new IllegalArgumentException("First page must bigger than 1!");
            }
        }
    }

    public static Double[] getCoordinate(String addr) throws IOException {
        Double lng = null;//经度
        Double lat = null;//纬度
        String address = null;
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String key = "f247cdb592eb43ebac6ccd27f796e2d2";
        String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        String addressInfo = null;

        try {
            if (myURL == null) {
                return null;
            }

            httpsConn = myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    addressInfo = addressInfo + temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (insr != null) {
                insr.close();
            }
            if (br != null) {
                br.close();
            }
        }

        if (StringUtils.isBlank(addressInfo)) {
            return null;
        }

        HashMap addressInfoResult = null;
        try {
            addressInfoResult = (HashMap) JSONUtil.deserialize(addressInfo.replace("null", ""));
            if (addressInfoResult == null) {
                return null;
            }

            HashMap result = (HashMap) addressInfoResult.get("result");
            if (result == null) {
                return null;
            }

            HashMap location = (HashMap) result.get("location");
            if (location == null) {
                return null;
            }

            lng = (Double) location.get("lng");
            lat = (Double) location.get("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Double[]{lng, lat};
    }

    /**
     * 生成验证码，总共长度为12位<br/>
     * 第一位表示验证资源的类型  如果订单是预约，以1开头；如果是够买疗程卡，以2开头；<br/>
     * 2-6位,时间戳后五位<br/>
     * 7-9位，每天从1自增数字，如果达到999，从1开始重新自增<br/>
     * 10-12位，999以内的随机数<br/>
     * @param jedis 不能为空
     * @param type 不能为空
     * @return 验证码
     */
    public static String genVerifyCode(MyjJedisCommend jedis, String type) {
        long currentTimeMillis = System.currentTimeMillis();
        String currentTimeStr = String.valueOf(currentTimeMillis);
        String prefix = currentTimeStr.substring(currentTimeStr.length() - 5, currentTimeStr.length());
        String key = "unique_code_date_prefix_" + DateUtil.convert(new Date(), DateUtil.DATE_PATTERN_yyyyMMdd);
        Long value = jedis.incr(key);
        int expireSeconds = DateUtil.getEndOfDaySeconds();
        jedis.expire(key, expireSeconds);
        if (value.intValue() >= 999) {
            jedis.set(key, "1");
            value = 999L;
        }

        String middle = NumberUtil.digitsFormat(value, 3);
        String tail = NumberUtil.digitsFormat(new Random().nextInt(999), 3);
        if (CmsConstant.ORDER_TYPE_APPOINTMENT.equals(type)) {
            type = "1";
        } else if (CmsConstant.ITEM_TYPE_WRAP.equals(type)) {
            type = "2";
        } else {
            throw new RuntimeException("Has no this type when genVerifyCode ,type is 【" + type + "】");
        }

        return type + prefix + middle + tail;
    }

}




