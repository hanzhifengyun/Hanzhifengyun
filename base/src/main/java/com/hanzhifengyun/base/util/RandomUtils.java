package com.hanzhifengyun.base.util;

/**
 * Created by jiangxk on 2016/12/22.
 */

public class RandomUtils {

    /**
     * 产生1-100随机数
     */
    public static int get1To100RandomInt() {
        return (int) (1 + Math.random() * 100);
    }

    /**
     * 产生a-b的随机数
     *
     * @param a 左边界
     * @param b 右边界
     * @return 范围内的随机数
     */
    public static int getAToBRandomInt(int a, int b) {
        return (int) (a + Math.random() * b);
    }
}
