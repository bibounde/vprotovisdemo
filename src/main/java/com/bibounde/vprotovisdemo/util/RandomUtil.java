package com.bibounde.vprotovisdemo.util;

import java.util.Random;

public class RandomUtil {

    private static final Random rd = new Random();
    
    public static double nextDouble(int max, boolean negative) {
        double val = Integer.valueOf(rd.nextInt(max)).doubleValue();
        return !negative || rd.nextBoolean() ? val : 1 - val;
    }
    
    public static String[] nextColors() {
        String[] ret = new String[20];
        for (int i = 0; i < 20; i++) {
            int r = rd.nextInt(255);
            int g = rd.nextInt(255);
            int b = rd.nextInt(255);
            
            StringBuilder color = new StringBuilder("#");
            if (r < 16) {
                color.append("0");
            }
            color.append(Integer.toHexString(r));
            if (g < 16) {
                color.append("0");
            }
            color.append(Integer.toHexString(g));
            if (b < 16) {
                color.append("0");
            }
            color.append(Integer.toHexString(b));
            
            ret[i] = color.toString();
        }
        return ret;
    }
}
