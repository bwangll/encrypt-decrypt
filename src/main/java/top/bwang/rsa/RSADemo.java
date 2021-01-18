package top.bwang.rsa;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author bwang
 * @version 1.0
 * @Description rsa加密算法
 * @date 2021/1/17 20:55
 */
public class RSADemo {
    public static void main(String[] args) throws Exception {
        // 加密算法
        String algorithm = "RSA";

        String input = "周吴郑王";
        //生成密钥保存到本地文件中
        //generateKeyToFile(algorithm, "a.pub", "a.pri");
        PrivateKey privateKey = getPrivateKey("a.pri", algorithm);
        PublicKey publicKey = getPublicKey("a.pub", algorithm);
        String encryptRSA = encryptRSA(algorithm, publicKey, input);
        String decryptRSA = decryptRSA(algorithm, privateKey, encryptRSA);
        System.out.println(encryptRSA);
        System.out.println(decryptRSA);
    }

    /**
     * 获取公钥
     *
     * @param publicPath 公钥目录地址
     * @param algorithm  算法
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String publicPath, String algorithm) throws Exception {
        //将文件转化为字符串
        String publicKey = FileUtils.readFileToString(new File(publicPath), Charset.defaultCharset());
        //获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //构建密钥工厂
        //X509EncodedKeySpec 这个类代表了ASN.1编码的公钥
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKey));
        //生成公钥
        return keyFactory.generatePublic(spec);
    }

    /**
     * 获取私钥
     *
     * @param privatePath 私钥目录地址
     * @param algorithm   算法
     * @return 私钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String privatePath, String algorithm) throws Exception {
        //将文件转为字符串
        String privateKey = FileUtils.readFileToString(new File(privatePath), Charset.defaultCharset());
        //获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //构建密钥规范 进行Base64编码
        //PKCS8EncodedKeySpec 这个类代表了ASN.1编码的私钥
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        //生成私钥
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 生成密钥对并保存在本地文件中
     *
     * @param algorithm 算法
     * @param pubPath   公钥保存路径
     * @param priPath   私钥保存路径
     * @throws Exception 异常
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception {
        //创建密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //生成公钥
        PublicKey publicKey = keyPair.getPublic();
        //生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //获取私钥字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        //获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        //对公私钥进行Base64编码
        String publicEncoded = Base64.encode(publicKeyEncoded);
        String privateEncode = Base64.encode(privateKeyEncoded);
        //保存文件
        FileUtils.writeStringToFile(new File(pubPath), publicEncoded, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(priPath), privateEncode, StandardCharsets.UTF_8);
    }

    public static String decryptRSA(String algorithm, Key key, String encrypted) throws Exception {
        //创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        //使用私钥进行解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] decode = Base64.decode(encrypted);
        byte[] bytes = cipher.doFinal(decode);
        System.out.println("bytes = " + new String(bytes));
        return new String(bytes);
    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm 算法
     * @param key       密钥
     * @param input     原文
     * @return 加密后的数据
     * @throws Exception 异常
     */
    public static String encryptRSA(String algorithm, Key key, String input) throws Exception {
        //创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化加密
        // 第一个参数:加密的模式
        // 第二个参数：使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        System.out.println("bytes = " + Base64.encode(bytes));
        return Base64.encode(bytes);
    }
}
