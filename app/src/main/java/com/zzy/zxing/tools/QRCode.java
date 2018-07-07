package com.zzy.zxing.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by BCLZzy on 2018/6/27.
 * 二维码工具类
 */

public class QRCode {

    private static final String TAG = "QRCode";

    /**
     * 生成二维码
     * @param url 要生成二维码的字符串
     * @param IWidth 二维码宽度
     * @param IHeight 二维码高度
     * @return
     * @throws WriterException
     */
    public Bitmap getQRCode(String url, int IWidth, int IHeight) throws WriterException {
        return getQRCode(url, IWidth, IHeight, 1);
    }

    /**
     * 生成二维码
     * @param url 要生成二维码的字符串
     * @param IWidth 二维码宽度
     * @param IHeight 二维码高度
     * @param margin 白边大小
     * @return
     * @throws WriterException
     */
    public Bitmap getQRCode(String url, int IWidth, int IHeight, int margin) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错率
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 编码格式
        hints.put(EncodeHintType.MARGIN, margin);// 可选值范围0-4，当值为0时，无白边，依次类推
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, IWidth, IHeight, hints);
        matrix = deleteWhite(matrix);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y * width + x] = (matrix.get(x, y)) ? 0xff000000 : 0xffffffff;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    // 删除白边
    private BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();// 获取二维码图案属性
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);// 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {// 循环，将二维码图案绘制到新的BitMatrix中
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    private BitMatrix deleteWhite(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * 生成二维码
     * @param url 要生成二维码的字符串
     * @param IWidth 二维码宽度
     * @param IHeight 二维码高度
     * @param icon 二维码中间显示图标
     * @return
     * @throws WriterException
     */
    public Bitmap getQRCode(String url, int IWidth, int IHeight, Bitmap icon) throws WriterException {
        return getQRCode(url, IWidth, IHeight, 1, icon);
    }

    /**
     * 生成二维码
     * @param url 要生成二维码的字符串
     * @param IWidth 二维码宽度
     * @param IHeight 二维码高度
     * @param margin 白边大小
     * @param icon 二维码中间显示图标
     * @return
     * @throws WriterException
     */
    public Bitmap getQRCode(String url, int IWidth, int IHeight, int margin, Bitmap icon) throws WriterException {
        // 图片宽度的一般
        int IMAGE_HALFWIDTH = IWidth/3/2;// icon是二维码的1/3
        Log.d(TAG, "IMAGE_HALFWIDTH=" + IMAGE_HALFWIDTH);
        icon=zoomBitmap(icon, IMAGE_HALFWIDTH);
        Hashtable<EncodeHintType, Object> hints=new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, margin);
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, IWidth, IHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    int iconPixel = icon.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                    if (((iconPixel & 0xff000000) >> 24) == 0) {
                        //若像素为透明,则设置为白色
                        pixels[y * width + x] = 0xffffffff;
                    } else {
                        pixels[y * width + x] = iconPixel;
                    }
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else { // 无信息设置像素点为白色
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
        }
        Bitmap bitmap_icon = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap_icon.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap_icon;
    }

    // 缩放图标
    private Bitmap zoomBitmap(Bitmap icon,int h){
        // 缩放图片
        if (h*2 <= icon.getWidth()) {
            return icon;
        }
        Matrix m = new Matrix();
        float sx = (float) 2 * h / (float)icon.getWidth();
        float sy = (float) 2 * h / (float)icon.getHeight();
        m.setScale(sx, sy);
        // 图片缩放（不可放大，会报异常）
        return Bitmap.createBitmap(icon, 0, 0,icon.getWidth(), icon.getHeight(), m, false);
    }

}
