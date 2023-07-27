package com.aqi.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class SignUtil {
    public static String createSign(String appSecret, String nonce, String timestamp) {
        String content = "appSecret=" + appSecret + "&nonce=" + nonce + "&timestamp=" + timestamp;
        try {
            String sign = sha(content);
            return sign;
        } catch (Exception e) {
            log.error("生成签名异常 {}", e);
            throw new RuntimeException("生成签名异常");
        }
    }

    public static String sha(String inputStr) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] byteArray = inputStr.getBytes("UTF-8");
            byte[] md5Bytes = sha.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            log.error("生成签名异常 {}", e);
            throw new RuntimeException("生成签名异常");
        }
    }

}
