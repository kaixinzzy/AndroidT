package com.zzy.event;


import com.zzy.util.ConvertByte;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class JavaRun {

    @Test
    public void test() {
        /*byte[] buf = new byte[] {0x00, 0x00, 0x02, 0x00, 0x00, 0x02};
        byte[] datas = Arrays.copyOfRange(buf,4,buf.length - 1);
        System.out.println(bytesToHexString(datas, datas.length, true));

        int i = buf[0] << 16 | buf[1] << 8 | buf[2];
        System.out.println(i);

        List<Boolean> array1 = getBooleanArray((byte) 0x03);
        List<Boolean> array2 = getBooleanArray((byte) 0x01);
        // 拼接两个byte
        Boolean b = array1.addAll(array2);
        if (b) {
            for (int i1 = 0; i1 < array1.size(); i1++) {
                System.out.print(array1.get(i1) ? "1" : "0");
            }
        }
        System.out.println();
        float f = 9.9f;
        int i1 = (int) f;
        System.out.println(i1 + " - ");
        // 获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        String recordTime = formatter.format(curDate);
        System.out.println(recordTime);
        // 20000628230039
        // 20190408150225
        byte[] bytes = hexStrToByteArray(recordTime);
        String s = byteArrayToStr(bytes);
        System.out.println(s);*/
        List<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        System.out.println(list);

    }

    /** String转byte[] **/
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    /** byte[]转String **/
    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    /** 十六进制String转byte[] **/
    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
            System.out.print(byteToHexString(byteArray[i], true) + " ");
        }
        return byteArray;
    }

    /** byte[]转十六进制String **/
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 将byte中的每位（bit）用boolean类型表示。
     * 0x03会打印11000000
     * @param b
     * @return
     */
    private List<Boolean> getBooleanArray(byte b) {
        List<Boolean> array = new ArrayList<>();
        for (int i=0; i<=7; i++) {
//            array[i] =(byte)(b & 1);
            array.add((b & 1) == 1);
            b = (byte)(b >> 1);
//            System.out.print(array.get(i) ? "1" : "0" );
        }
        return array;
    }

    /** BCD码（日期、时间、帐号等）转String；（可以将此String强转为int类型） **/
    @Test
    public void BCDbytes2String() {
        byte[] bytes = new byte[]{0x20, 0x15, 0x05, 0x13, 0x14, 0x30, 0x28};
        String BCD = bytesToHexString(bytes, bytes.length, false);
        System.out.println(BCD);
        byte[] bytes1 = Arrays.copyOfRange(bytes, 1, 3);
        System.out.println(bytesToHexString(bytes1, bytes1.length, true));
    }

    /** byte转HexString **/
    public static String byteToHexString(byte b, boolean flag) {
        if (flag) {
            return String.format("0x" + "%02x", b);
        } else {
            return String.format("%02x", b);
        }
    }

    /** bytes转HexString **/
    private String bytesToHexString(byte[] bytes, int size, boolean flag) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (flag) {
                builder.append("0x" + String.format("%02x", bytes[i]) + " ");
            } else {
                builder.append(String.format("%02x", bytes[i]));
            }
        }
        return builder.toString();
    }

    /** 两个byte转int，高位先发 **/
    @Test
    public void twoByte2Int() {
        // int 5330
//        byte highByte = (byte) 0x14;// 高位
//        byte lowByte = (byte) 0xd2;// 低位
        // int -90
        byte highByte = (byte) 0xFF;// 高位
        byte lowByte = (byte) 0xA6;// 低位
        int high = highByte << 8;
        int low = lowByte & 0xFF;
        int temp = high|low;
        System.out.println("high is " + high);
        System.out.println("low is " + low);
        System.out.println("temp is " + temp);
    }

    /** int转两个byte，高位先发 **/
    @Test
    public void int2TwoByte() {
        int i = -90;
        int highInt = i & 0x0000FF00;
        byte highByte = (byte) (highInt >> 8);
        byte lowByte = (byte) (i & 0x000000FF);
        String highStr = ConvertByte.toHexString(highByte);
        String lowStr = ConvertByte.toHexString(lowByte);
        System.out.println("high is " + highStr);
        System.out.println("low is " + lowStr);
    }

    @Test
    public void string() {
//        String tip = "aaa\nbbb\nccc\nddd\neee\n";
        String tip = "aaa\n";
        int from = tip.indexOf("\n", 0);
        String[] splits = tip.split("\n");

        System.out.println(from + "");
        System.out.println(splits.length + "");

        int index = (splits.length <= 3) ? 0 : (splits.length -3);
        for (int i = index; i < splits.length; i++) {
            System.out.println(splits[i]);
        }
    }

    @Test
    public void ParseLong() {
        System.out.println(String.format(Locale.getDefault(), "%012d", Long.parseLong("123")));
        // 只去整数
        int i = Math.round(Float.valueOf("0.01")*100f);
        String s = String.valueOf(i);
        System.out.println(s);
        System.out.println(String.format(Locale.getDefault(), "%012d", Long.parseLong(s, 10)));
    }

    @Test
    public void Vector() {
        Vector<String> stringVector = new Vector<>();
        stringVector.add("a");
        stringVector.add("b");
        stringVector.add("c");

        System.out.println(stringVector.get(0));

        stringVector.remove(0);
        stringVector.remove(0);
        if (stringVector.size() != 0) {
            System.out.println(stringVector.get(0));
        } else {
            System.out.println("Vector size is 0");
        }
    }

    public void checkBCC(byte[] datas,byte checkData){
        boolean flag = false;
        int result = 0;
        for (int i = 0; i <datas.length; i++) {
            result = result ^ ((int)datas[i]);
        }
        int data= (int)checkData;
        if (result==data){
            flag =  true;
        }
        System.out.println("BCC（又名XOR 异或校验算法）结果：" + flag);
    }

    public byte getBCC(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < (bytes.length); i++) {
            result = result ^ ((int)bytes[i]);
        }
        System.out.println("根据byte数组，计算BCC校验值： " + (byte) result);
        return (byte) result;
    }

    @Test
    public void testBCC() {
        byte[] datas = {0x02, 0x03, 0x01, 0x00, 0x00, 0x03};
        JavaRun javaRun = new JavaRun();
        javaRun.checkBCC(datas, (byte) 0x03);

        javaRun.getBCC(datas);
    }

    protected byte getSum(byte[] bytes) {
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            sum = sum + (int) bytes[i];
        }
        return (byte) (sum & 0xFF);// 只保留一位
    }

    @Test // 和校验，只保留1位
    public void testSum() {
        byte[] datas = {0x00, 0x22};
        JavaRun javaRun = new JavaRun();
        byte b = javaRun.getSum(datas);
        System.out.print("和校验:" + b);
    }

    /**
     * String类型的钱数，转换成byte[]
     */
    @Test
    public void Convert() {
        String money = "12.66";
        float moneyF = Float.parseFloat(money);
        int moneyI = Math.round(moneyF * 100f);
        byte[] bytes = ByteBuffer.allocate(4).putInt(moneyI).array();
        for (int i = 0; i < bytes.length; i++) {
            System.out.format("0x%x ", bytes[i]);
        }
    }

    /**
     * switch的不同case中，定义同名变量，必须加{}，因为不加括号的话，变量位于相同作用域。
     * 相同作用域不可以定义同名变量，不然编译器不知道调用谁。
     */
    @Test
    public void actionScope() {
        int i =  2;// 模拟数据
        int age = 0;
        switch (i) {
            case 0:
                int age1 = i;
                break;
            case 1:
//                int age = i;//报错，因为switch外面一定定义了age，它们位于相同作用域
//                int age1 = i;//报错，因为case 0中一定定义了age1，它们位于相同作用域
                int age2 = i;
                break;
            case 2: {
                int age3 = i;
                break;
            }
            case 3: {
                int age3 = i;
                break;
            }
        }
        System.out.println("------");
    }

    /**
     * 1、return会终止函数（即方法）
     * 2、break会终止循环
     */
    @Test
    public void return_break(){
        int count = 100;
        for (int i = 0; i < count; i++) {
            if (i == 3){
                break;//终止for循环（）不止for循环，所有循环都可以终止
            }
            if (i == 2) {
                return;//终止return_break()方法
            }
        }
    }

    /**
     * 1、try catch到的异常，不会引起程序崩溃，程序可继续向下执行。
     * 2、未try catch到的异常，程序直接崩溃。
     * 3、主动throw new Exception，程序崩溃；用于提示用户崩溃信息，用户可以将此信息通知程序员。
     */
    @Test
    public void try_catch_throw_exception(){
        String s = null; // 模拟null强制类型转换报错
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();//打印异常信息，程序继续向下执行
        }
        System.out.println("异常已被捕获 e.printStackTrace()");

        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            //异常已被捕获，这里即使任何事情都不做，程序也可以继续往下执行
        }
        System.out.println("异常已被捕获");

        //这样程序也会直接崩溃，后面代码不会执行；崩溃信息显示java内部异常定义的信息
        //Integer.parseInt(s);

        if (null == s) {
            //主动抛出异常，程序终止，后面的代码不会执行
            throw new IllegalArgumentException("我是 IllegalArgumentException");
        }
        System.out.println("主动抛出异常，程序终止，此句不会打印");
    }

    @Test
    public void try_finally_return_T() {
        try_finally_return();
    }

    // try finally return 执行过程分析
    public int try_finally_return(){
        int i = 1;
        try {
            // 在try里返回值会先存到一个临时变量中，finally里改变的是原始变量，
            // 改完之后再将临时变量的值return(返回)，也就是说在finally里改变返回值变量并不影响返回值本身。
            return i;
        } catch (Exception e) {
            return i;
        } finally {
            i++;
            // 可见如果finally和try里都有执行了return，try里的return的值会被废弃。
            //  return i;
        }
    }

    /**
     * 1、当map.get(key)时，map中没有相应的key时，是会报异常，还是返回null
     *      结果：返回null
     * 2、map中key是唯一的，如果再次插入已有的键值对，那么会覆盖以存在key的value值.
     */
    @Test
    public void MapGetKeyIsNull(){
        HashMap<String, String> arrayMap = new HashMap<>();
        arrayMap.put("a", "a");
        arrayMap.put("b", "b");

        String result = arrayMap.get("c");
        if (null == result){
            System.out.println("结果是null");
        } else {
            System.out.println("结果是 " + result);
        }
    }

    // 取余数
    @Test
    public void getRemainder(){
        System.out.println("0 % 3 = " + (0 % 3));// 0 % 3 = 0
        System.out.println("1 % 3 = " + (1 % 3));// 1 % 3 = 1
        System.out.println("2 % 3 = " + (2 % 3));// 2 % 3 = 2
        System.out.println("3 % 3 = " + (3 % 3));// 3 % 3 = 0
    }

    @Test
    public void break_return_continue() {
        for (int i = 0; i <10 ; i++) {
            if (i==2) {
                continue;//等于2时，下面的代码不执行，直接进入值等于3的循环
            }
            if (i == 4) {
                break;//结束for循环
            }
            System.out.println(i);
        }
        //跳出多层for循环
        wc:for (int i = 0; i < 10; i++) {
            nc:for (int j = 0; j < 10; j++) {
                if (j==2)
//                    break;//这样只能结束nc层循环
                    break wc;//结束指定wc层循环
                System.out.println("i:" + i + " j:" + j);
            }
        }
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                return;//跳出test方法
            }
        }

        System.out.println("玩完了");
    }

}
