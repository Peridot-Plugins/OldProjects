package me.mckoxu.mckrtp.utils;

import org.apache.commons.lang.Validate;

import java.util.Random;

public class RandomUtil {
    private static final Random RAND = new Random();

    public static double getRandom(int min, int max) throws IllegalArgumentException{
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return RAND.nextInt(max - min + 1) + min;
    }
}
