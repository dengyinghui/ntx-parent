package com.ntx.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    //加密算法
    private static final String ALGORITHM = "RSA";

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 密钥大小 / 8 - 11
     * 1024 / 8 - 11 = 117
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 128;



    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static JSONObject genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥对生成器，密钥大小为1024 bit
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publicKey", publicKeyString);
        jsonObject.put("privateKey", privateKeyString);
        return jsonObject;
    }


    /**
     * 公钥加密
     * @param plaintext 明文
     * @param publicKey 公钥
     */
    public static String encrypt(String plaintext, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        byte[] data = plaintext.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 签名(私钥签名)
     * @param plaintext 明文
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(String plaintext, String privateKey) throws Exception{
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        byte[] privateKeyBytes = java.util.Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateK = factory.generatePrivate(pkcs8EncodedKeySpec);
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(privateK);
        sign.update(plaintext.getBytes(CHARSET_NAME));
        byte[] signBytes = sign.sign();
        return Base64.encodeBase64String(signBytes);
    }

    public static void main(String[] args) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < 100000;i++){
            stringBuilder.append("总共");
        }
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOsHjaaO/Rw91rcJdP//gfqXrIiukV5w97oX5Exlv2iR4yj91oNmRCfSPCTV+a+HB3G99cDuk2HvUpN+/fysl37IgPG2pLvAwK+XZ41xd7alk1Qhy2oFtcyULM6ffCFC2ziSTKpZIfqzxuqTxtyYQvcjXUZCHAhT3lruzab2iyNQIDAQAB";
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI6weNpo79HD3Wtwl0//+B+pesiK6RXnD3uhfkTGW/aJHjKP3Wg2ZEJ9I8JNX5r4cHcb31wO6TYe9Sk379/KyXfsiA8baku8DAr5dnjXF3tqWTVCHLagW1zJQszp98IULbOJJMqlkh+rPG6pPG3JhC9yNdRkIcCFPeWu7NpvaLI1AgMBAAECgYBTw8NerGXE5c+xR+p8G09E/xBGdE26/ub28+oVY8EO7JXeptgaWnvnEGGSqitq5VfYhHNOLq63h9ryNJVL8lbseaR6lgPtvbZdHNp6awp/pL/whfU6l15UTPlIBa6lbfpV1qK8b0IUAhOm+Ej7565+lC+6rKfBV25wayY6g/MWzQJBAPRtiyN7lWNPflLSc1Hchr53DQZn1pIbZCsIrorCRNNgULvWxXzygMvWD/hjmUpRvG26QjsJY1g9VJ5Q/ePQvv8CQQCVcdx7zGESNbvJW4EYau7tZtK5H4P3fK2PCedWNwTZ5/hPr7h6UyTqs16vQBX5gHxhwAB0eBGXcPsKHDaL3MLLAkBWyrr4S369N9HsSMwoCj1STmWym5aGZPGJklif7uGPiQjB2j5f4qA3QPiEw6D/0cwtzlCMNAYxo/1zBeFRTqMzAkAKhzf6PL7F8qUlHV4t4zKr5OeJhS/d2pm7jv4OM9gSpeX3bzC25W6fXj2YaRJjPmsE0+r0SZUrrxjwcGZjWg0bAkBlvL5UVGpqf0diV2Da1pDet5vR95AWnGDoBQi7VyTDVHHr/nmTdMqflBStOgnAR4iX2lcu5KCYZasjUGT3nf4G";
        /*String message = encrypt(stringBuilder.toString(), publicKey);
        System.out.println(message);
        System.out.println(decrypt(message, privateKey));*/

        String message = sign(stringBuilder.toString(), privateKey);
        System.out.println(message);
        System.out.println(verifySign(stringBuilder.toString(), message, publicKey));

    }


    /**
     * 验签(公钥验签)
     * @param plaintext 明文
     * @param sign 返回的签名
     * @param publickey 公钥
     * @return
     * @throws Exception
     */
    public static boolean verifySign(String plaintext, String sign, String publickey) throws Exception{
        byte[] publicKeyBytes = Base64.decodeBase64(publickey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicK = factory.generatePublic(x509EncodedKeySpec);
        Signature verifySign = Signature.getInstance("SHA1withRSA");
        verifySign.initVerify(publicK);
        verifySign.update(plaintext.getBytes(CHARSET_NAME));
        boolean signedSuccess = verifySign.verify(Base64.decodeBase64(sign));
        return signedSuccess;
    }


    /**
     * 私钥解密
     * @param ciphertext 密文
     * @param privateKey 私钥
     */
    public static String decrypt(String ciphertext, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        byte[] encryptedData = Base64.decodeBase64(ciphertext);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

}
