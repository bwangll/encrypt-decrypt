package top.bwang.desaes;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author bwang
 * @version 1.0
 * @Description TODO
 * @date 2021/1/17 1:15
 */
public class DesAesDemo {
    public static void main(String[] args) throws Exception {
        //原文    如果使用不填充模式，原文必须是8的整数倍
        String input = "周吴郑王";
        //des加密必须是8位
        String key = "12345678";
        //算法  f8I/WNXpqNbFUdOwPKGWLg==
        String algorithm = "DES";

        //String transformation = "DES";
        // ECB表示加密模式
        // PKCS5Padding表示填充模式 (该模式为默认模式)        f8I/WNXpqNbFUdOwPKGWLg==
        //String transformation = "DES/ECB/PKCS5Padding";
        //  B808O2LCAHJ8D2XGyZdTfQ==
        //String transformation = "DES/CBC/PKCS5Padding";
        //  B808O2LCAHJ8D2XGyZdTfQ==
        String transformation = "DES/CBC/PKCS5Padding";
        //加密
        String encryptDES = encryptDES(input, key, algorithm, transformation);
        System.out.println("加密: " + encryptDES);
        //解密
        String decryptDES = decryptDES(encryptDES, key, algorithm, transformation);
        System.out.println("解密: " + decryptDES);

    }

    private static String encryptDES(String input, String key, String algorithm, String transformation) throws Exception {
        // Cipher：密码，获取加密对象
        // transformation:参数表示使用什么类型加密
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定秘钥规则
        // 第一个参数表示：密钥，key的字节数组
        // 第二个参数表示：算法
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        //创建iv向量，是使用到CBC加密模式
        IvParameterSpec ivParameterSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
        //进行加密
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        // 打印字节，因为ascii码有负数，解析不出来，所以乱码
        return Base64.encode(bytes);
    }

    private static String decryptDES(String input, String key, String algorithm, String transformation) throws Exception {
        //获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定密钥规则
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        //创建iv向量，是使用到CBC加密模式
        IvParameterSpec ivParameterSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        //对解密进行初始化
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
        //进行解密
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        //因为时明文，直接返回
        return new String(bytes);
    }
}
