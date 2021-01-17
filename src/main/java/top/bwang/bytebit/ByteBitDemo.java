package top.bwang.bytebit;

/**
 * @author bwang
 * @version 1.0
 * @Description TODO
 * @date 2021/1/17 1:01
 */
public class ByteBitDemo {
    public static void main(String[] args) throws Exception{

        String a = "周";
        byte[] bytes = a.getBytes("GBK");
        for (byte b : bytes) {
            System.out.print(b + "   ");
            String s = Integer.toBinaryString(b);
            System.out.println(s);
        }
    }


}

