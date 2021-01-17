package top.bwang.ascii;

/**
 * @author bwang
 * @version 1.0
 * @Description AsciiDemo
 * @date 2021/1/16 22:37
 */
public class AsciiDemo {
    public static void main(String[] args) {
        String string = "AaZ";
        // 获取ascii码，需要把字符串转成字符
        char[] chars = string.toCharArray();
        for (char aChar : chars) {
            int print = aChar;
            System.out.println(print);
        }
    }
}
