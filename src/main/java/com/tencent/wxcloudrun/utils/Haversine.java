package com.tencent.wxcloudrun.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class Haversine {
    // 地球半径（单位：千米），使用 BigDecimal 表示
    private static final BigDecimal R = new BigDecimal("6371");

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 两点之间的距离，单位：千米
     */
    public static BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        // 将角度转换为弧度
        BigDecimal lat1Rad = toRadians(lat1);
        BigDecimal lon1Rad = toRadians(lon1);
        BigDecimal lat2Rad = toRadians(lat2);
        BigDecimal lon2Rad = toRadians(lon2);

        // 计算经度和纬度的差值
        BigDecimal dLat = lat2Rad.subtract(lat1Rad);
        BigDecimal dLon = lon2Rad.subtract(lon1Rad);

        // Haversine 公式
        BigDecimal a = BigDecimal.valueOf(Math.sin(dLat.doubleValue() / 2)).pow(2)
                .add(BigDecimal.valueOf(Math.cos(lat1Rad.doubleValue())).multiply(BigDecimal.valueOf(Math.cos(lat2Rad.doubleValue())))
                        .multiply(BigDecimal.valueOf(Math.sin(dLon.doubleValue() / 2)).pow(2)));

        BigDecimal c = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1 - a.doubleValue()))));

        // 计算并返回两点之间的距离
        return R.multiply(c);
    }

    /**
     * 将角度转为弧度
     *
     * @param angle 角度值
     * @return 对应的弧度值
     */
    public static BigDecimal toRadians(BigDecimal angle) {
        // 使用 MathContext 指定精度
        return angle.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal("180"), MathContext.DECIMAL128);
    }
}
