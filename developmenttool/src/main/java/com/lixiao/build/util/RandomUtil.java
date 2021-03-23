package com.lixiao.build.util;

import java.util.Random;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class RandomUtil {
    private static RandomUtil randomUtil;
    private Random random;
    private RandomUtil() {
        random=new Random();
    }

    public static RandomUtil getInstance() {
        if (randomUtil == null) {
            synchronized (RandomUtil.class) {
                if (randomUtil == null) {
                    randomUtil = new RandomUtil();
                }
            }
        }
        return randomUtil;
    }

    public String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public String getRandomString(int length, String str) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int getRandomInt(int i){
        return random.nextInt(i);
    }
}
