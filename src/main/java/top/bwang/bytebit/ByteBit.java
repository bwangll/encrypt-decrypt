package top.bwang.bytebit;

/**
 * @author bwang
 * @version 1.0
 * @Description ByteBit
 * @date 2021/1/17 1:00
 */
public class ByteBit {
    public static void main(String[] args) {
        String a = "a";
        byte[] bytes = a.getBytes();
        for (byte aByte : bytes) {
            int c = aByte;
            // 打印发现byte实际上就是ascii码
            System.out.println(c);
            // 我们在来看看每个byte对应的bit，byte获取对应的bit
            String s = Integer.toBinaryString(c);
            System.out.println(s);
        }
    }
}
