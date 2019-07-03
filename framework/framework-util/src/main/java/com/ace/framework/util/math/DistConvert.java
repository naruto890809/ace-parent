package com.ace.framework.util.math;

/**
 * @author WuZhiWei
 * @since 2016-01-14 13:49
 */
public class DistConvert {
    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static float calculateLineDistance(Double longitude,Double latitude,Double toLongitude,Double toLatitude) {

        if(latitude==-1 && latitude==-1){
            return -1;
        }

        double var2 = 0.01745329251994329D;
        double var4 = longitude;
        double var6 = latitude;
        double var8 =toLongitude;
        double var10 =toLatitude;
        var4 *= 0.01745329251994329D;
        var6 *= 0.01745329251994329D;
        var8 *= 0.01745329251994329D;
        var10 *= 0.01745329251994329D;
        double var12 = Math.sin(var4);
        double var14 = Math.sin(var6);
        double var16 = Math.cos(var4);
        double var18 = Math.cos(var6);
        double var20 = Math.sin(var8);
        double var22 = Math.sin(var10);
        double var24 = Math.cos(var8);
        double var26 = Math.cos(var10);
        double[] var28 = new double[3];
        double[] var29 = new double[3];
        var28[0] = var18 * var16;
        var28[1] = var18 * var12;
        var28[2] = var14;
        var29[0] = var26 * var24;
        var29[1] = var26 * var20;
        var29[2] = var22;
        double var30 = Math.sqrt((var28[0] - var29[0]) * (var28[0] - var29[0]) + (var28[1] - var29[1]) * (var28[1] - var29[1]) + (var28[2] - var29[2]) * (var28[2] - var29[2]));
        return (float)(Math.asin(var30 / 2.0D) * 1.27420015798544E7D);
    }
}
