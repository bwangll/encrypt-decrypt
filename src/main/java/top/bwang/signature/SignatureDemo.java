package top.bwang.signature;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import top.bwang.rsa.RSADemo;

/**
 * @author bwang
 * @version 1.0
 * @Description 签名认证方法
 * @date 2021/1/18 11:18
 */
public class SignatureDemo {
    public static void main(String[] args) throws Exception {
        String input = "123";

        PublicKey publicKey = RSADemo.getPublicKey("a.pub", "RSA");
        PrivateKey privateKey = RSADemo.getPrivateKey("a.pri", "RSA");
        //生成签名
        String signatureData = getSignature(input, "sha256withrsa", privateKey);
        //校验签名
        boolean verifySignature = verifySignature(input, "sha256withrsa", publicKey, signatureData);
        System.out.println("verifySignature = " + verifySignature);
    }

    /**
     * 生成签名
     *
     * @param input      原文
     * @param algorithm  算法
     * @param privateKey 私钥
     * @return 签名
     * @throws Exception 异常
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception {
        //获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        //初始化签名
        signature.initSign(privateKey);
        //传入原文
        signature.update(input.getBytes(StandardCharsets.UTF_8));
        //开始签名
        byte[] sign = signature.sign();
        //对签名的数据进行Base64编码
        return Base64.encode(sign);
    }

    /**
     * 验证签名
     *
     * @param input 原文
     * @param algorithm 算法
     * @param publicKey 公钥
     * @param signatureData 签名
     * @return 结果
     * @throws Exception 异常
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signatureData) throws Exception {
        //获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initVerify(publicKey);
        //传入原文
        signature.update(input.getBytes(StandardCharsets.UTF_8));
        //校验签名
        return signature.verify(Base64.decode(signatureData));
    }
}
