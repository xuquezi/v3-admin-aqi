package com.aqi.common.core.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    private static final String ENCODE_KEY = "aqi1221ghfgs1120";

    public static String encryptFromString(String content) {
        AES aes = SecureUtil.aes(ENCODE_KEY.getBytes(StandardCharsets.UTF_8));
        String encryptHex = aes.encryptHex(content);
        return encryptHex;
    }

    public static String decryptFromString(String data) {
        AES aes = SecureUtil.aes(ENCODE_KEY.getBytes(StandardCharsets.UTF_8));
        String decryptStr = aes.decryptStr(data, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }

    public static void main(String[] args) {
        String s = encryptFromString("123456");
        System.out.println(s);
    }
}
