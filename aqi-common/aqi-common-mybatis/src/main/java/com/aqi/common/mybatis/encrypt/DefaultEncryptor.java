package com.aqi.common.mybatis.encrypt;

import cn.hutool.crypto.SecureUtil;

public class DefaultEncryptor implements IEncryptor {
    public static final byte[] AES_KEY = "VH2B9DB95VONPA5C".getBytes();

    @Override
    public String encrypt(String str) {
        return SecureUtil.aes(AES_KEY).encryptHex(str);
    }

    @Override
    public String decrypt(String str) {
        return SecureUtil.aes(AES_KEY).decryptStr(str);
    }
}
