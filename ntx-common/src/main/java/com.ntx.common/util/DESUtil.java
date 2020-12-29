package com.ntx.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtil {

    public static void main(String[] args) {
        String secretKey = "";
        String data = "";
        String p = encryptBasedDes(data, secretKey);
        System.out.println(p);
        System.out.println(decryptBasedDes(p, secretKey));
    }

    /**
     * 加密
     * @param data 明文
     * @param secretKey 密钥
     * @return
     */
    public static String encryptBasedDes(String data, String secretKey) {
        String encryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            byte[] bytes = secretKey.getBytes();
            DESKeySpec deskey = null;
            if(bytes.length < 8){
                byte[] b = new byte[8];
                System.arraycopy(bytes, 0, b, 0, bytes.length);
                deskey = new DESKeySpec(b);
            } else{
                deskey = new DESKeySpec(bytes);
            }

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * 解密
     * @param cryptData 密文
     * @param secretKey 密钥
     * @return
     */
    public static String decryptBasedDes(String cryptData, String secretKey) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            byte[] bytes = secretKey.getBytes();
            DESKeySpec deskey = null;
            if(bytes.length < 8){
                byte[] b = new byte[8];
                System.arraycopy(bytes, 0, b, 0, bytes.length);
                deskey = new DESKeySpec(b);
            } else{
                deskey = new DESKeySpec(bytes);
            }
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }



}
