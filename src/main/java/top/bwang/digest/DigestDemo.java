package top.bwang.digest;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @author bwang
 * @version 1.0
 * @Description 获取字符串消息摘要
 * @date 2021/1/17 16:34
 */
public class DigestDemo {
    public static void main(String[] args) throws Exception{
        String input = "aa";
        String algorithm = "MD5";

        // sha1 可以实现秒传功能

        String sha1 = getDigestFile("apache-tomcat-9.0.10-windows-x64.zip", "SHA-1");
        System.out.println(sha1);

        String sha512 = getDigestFile("apache-tomcat-9.0.10-windows-x64.zip", "SHA-512");
        System.out.println(sha512);

        String md5 = getDigest("aa", "MD5");
        System.out.println(md5);

        String md51 = getDigest("aa ", "MD5");
        System.out.println(md51);
    }

    /**
     * 获取文件摘要信息
     * @param filePath 文件路径
     * @param algorithm 使用的算法 [MD5, SHA-1, SHA-256, SHA-512]
     * @return 16进制字符串
     * @throws Exception 异常
     */
    private static String getDigestFile(String filePath, String algorithm) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        int len;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((len = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        //获取消息摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        //获取消息摘要
        byte[] digest = messageDigest.digest(byteArrayOutputStream.toByteArray());
        System.out.println("密文的字节长度：" + digest.length);
        return toHex(digest);

    }

    /**
     * 获取字符串的摘要信息
     * @param input
     * @param algorithm
     * @return
     * @throws Exception
     */
    private static String getDigest(String input, String algorithm) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] digest = messageDigest.digest(input.getBytes());
        System.out.println("密文的字节长度："+digest.length);
        return toHex(digest);
    }

    /**
     * 转16进制
     * @param digest 字节数组
     * @return 16进制字符串
     */
    private static String toHex(byte[] digest) {
        //        System.out.println(new String(digest));
        // 消息摘要进行表示的时候，是用16进制进行表示
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            // 转成16进制

            String s = Integer.toHexString(b & 0xff);
            // 保持数据的完整性，前面不够的用0补齐
            if (s.length()==1){
                s="0"+s;
            }
            sb.append(s);
        }
        System.out.println("16进制数据的长度:"+ sb.toString().getBytes().length);
        return sb.toString();
    }
}
