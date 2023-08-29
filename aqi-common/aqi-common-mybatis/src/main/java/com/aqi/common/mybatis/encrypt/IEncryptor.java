package com.aqi.common.mybatis.encrypt;

public interface IEncryptor {

    String encrypt(String str);

    String decrypt(String str);
}
