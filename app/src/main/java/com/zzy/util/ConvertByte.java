package com.zzy.util;

import java.nio.ByteBuffer;

/**
 * Byte的数据类型转换
 */

public class ConvertByte {

    public static void main(String args[]) {
        {
            /** hex bytes 转换成int
             *  1个byte占8位，1个int占32位，所有要转换成int类型，byte数组的长度为4个byte
             *  关于机器传float or double类型，一般都是将传递的hex bytes类型先转换成int，然后/100f or /100d
             */
            byte[] bytes = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01};
            String hexString = toHexString(bytes, false);
            int i = Integer.decode(hexString);

            System.out.println("bytes toHexString option false " + hexString);
            System.out.println("bytes toHexString option true " + toHexString(bytes, true));
            System.out.println("bytes to int " + i);
            System.out.println("bytes toFloat " + i/100f);
            System.out.println("bytes toDouble " + i/100d);
        }
        {
            /**
             * 将float or double类型的数据（eg：商品价格等）传递给机器
             * 如0.01d单位为元
             * int i = (int) (0.01d * 100） 转换成int类型，此时单位为分。
             * 然后将此int类型的i转换成byte数组，传递给机器即可。
             * 1个byte占8位，1个int占32位，所有要转换成int类型，byte数组的长度为4个byte
             */
            byte[] bytesInt = new byte[4];

            float f = 0.01f;
            int i = (int) (f * 100);

            ByteBuffer.wrap(bytesInt).putInt(i);
            System.out.println("Float to Hex Bytes " + toHexString(bytesInt, true));

            double d = 0.01d;
            int j = (int) (d * 100);

            ByteBuffer.wrap(bytesInt).putInt(j);
            System.out.println("Double to Hex Bytes " + toHexString(bytesInt, true));


        }
        {
            /**
             * 请参照ASCII码表完整版（0x00-0x7F） 表格
             * 此表将每一个hex byte对应一个字符、按键、数字、大写字母、小写字母等
             */
            byte[] bytes = new byte[]{(byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33};
            String hexString = "30313233";//30-0 31-1 32-2 33-3

            System.out.println("bytes toAscii " + toAscii(bytes));
            System.out.println("hexString toAscii " + toAscii(hexString));
        }
    }

    /**
     * 将Hex码以String形态显示，一般打印时用
     * @param bytes
     * @param flag 返回结果类型 true:0x00 false:00
     * @return
     */
    private static String toHexString(byte[] bytes, boolean flag) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : bytes) {
            if (flag) {
                builder.append("0x" + String.format("%02x", b) + " ");
            } else {
                builder.append(String.format("%02x", b));
            }
        }
        return builder.toString();
    }

    private String toHexString(byte b) {
        return String.format("%02x", b);
    }

    /** bytes 转 Ascii码 **/
    public static String toAscii(byte[] bytes) {
        String str = toHexString(bytes, false);
        return toAscii(str);
    }

    /** hexString 转 Ascii码 **/
    public static String toAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }



}
