/*
 * 文件名：DecimalPlaces.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.common.enump;

/**
 * 小数位枚举
 * @author Administrator
 * @version 2014年10月16日
 * @see DecimalPlaces
 * @since
 */
public enum DecimalPlaces {
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4);    //    调用构造函数来构造枚举项

    private int value = 0;

    private DecimalPlaces(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }

    public static DecimalPlaces valueOf(int value) {    //    手写的从int到enum的转换函数
        switch (value) {
        case 0:
            return ZERO;
        case 1:
            return ONE;
        case 2:
            return TWO;
        case 3:
            return THREE;
        case 4:
            return FOUR;
        default:
            return null;
        }
    }

    public int value() {
        return this.value;
    }
}