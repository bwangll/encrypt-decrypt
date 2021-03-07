package top.bwang.desaes;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author bwang
 * @version 1.0
 * @Description TODO
 * @date 2021/1/17 1:15
 */
public class AesDemo {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //原文
        String input = "周吴郑王";
        //des加密必须是8位
        String key = "1234567812345678";
        //算法
        String algorithm = "AES";

        String transformation = "AES";
        //加密
        String encryptDES = encryptDES(input, key, algorithm, transformation);
        System.out.println("加密" + encryptDES);
        //解密
        String decryptDES = decryptDES(encryptDES, key, algorithm, transformation);
        System.out.println(decryptDES);

    }

    private static String encryptDES(String input, String key, String algorithm, String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Cipher：密码，获取加密对象
        // transformation:参数表示使用什么类型加密
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定秘钥规则
        // 第一个参数表示：密钥，key的字节数组
        // 第二个参数表示：算法
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        // 对加密进行初始化
        // 第一个参数：表示模式，有加密模式和解密模式
        // 第二个参数：表示秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        //进行加密
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        // 打印字节，因为ascii码有负数，解析不出来，所以乱码
        return Base64.encode(bytes);
    }

    private static String decryptDES(String input, String key, String algorithm, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定密钥规则
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        //对解密进行初始化
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        //进行解密
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        //因为时明文，直接返回
        return new String(bytes);
    }
}
