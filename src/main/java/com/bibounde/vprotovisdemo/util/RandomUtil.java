package com.bibounde.vprotovisdemo.util;

import java.util.Random;

public class RandomUtil {

    private static final Random rd = new Random();
    
    public static double nextDouble(int max) {
        double val = Integer.valueOf(rd.nextInt(max)).doubleValue();
        return rd.nextBoolean() ? val : 1 - val;
    }
}
