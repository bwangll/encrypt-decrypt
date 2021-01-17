package top.bwang.kaiser;

/**
 * @author bwang
 * @version 1.0
 * @Description 凯撒加密
 * @date 2021/1/17 0:43
 */
public class KaiserDemo {
    public static void main(String[] args) {
        String original = "Hello world";
        // 往右边偏移三位
        int key = 3;
        // 选中我即将抽取的代码，按快捷键Ctrl + Alt + M
        String encryptKaiser =  encryptKaiser(original,key);
        System.out.println("加密：" + encryptKaiser);
        String decryptKaiser =  decryptKaiser(encryptKaiser,key);
        System.out.println("解密：" + decryptKaiser);
    }
    /**
     * 使用凯撒加密方式解密数据
     *
     * @param encryptedData :密文
     * @param key           :密钥
     * @return : 源数据
     */
    public static String decryptKaiser(String encryptedData, int key) {
        // 将字符串转为字符数组
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode -= key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }
        return sb.toString();
    }
    /**
     * 使用凯撒加密方式加密数据
     *
     * @param orignal :原文
     * @param key     :密钥
     * @return :加密后的数据
     */
    public static String encryptKaiser(String orignal, int key) {
        // 将字符串转为字符数组
        char[] chars = orignal.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            // 获取字符的ascii编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode += key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }
        return sb.toString();
    }
}
