package com.zzy.zxing.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.Map;

/**
 * Created by BCLZzy on 2018/7/5.
 */

public class MyQRCodeWriter implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    /**
     * 对 zxing 的 QRCodeWriter 进行扩展, 解决白边过多的问题
     * <p/>
     * 源码参考 {@link com.google.zxing.qrcode.QRCodeWriter#renderResult(QRCode, int, int, int)}
     *
     * @param code
     * @param width
     * @param height
     * @param quietZone 取值 [0, 4]
     * @return
     */
    private BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // xxx 二维码宽高相等, 即 qrWidth == qrHeight
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);


        // 白边过多时, 缩放
        int minSize = Math.min(width, height);
        int scale = calculateScale(qrWidth, minSize);
        if (scale > 0) {
            int padding, tmpValue;
            // 计算边框留白
            padding = (minSize - qrWidth * scale) / QUIET_ZONE_SIZE * quietZone;
            tmpValue = qrWidth * scale + padding;
            if (width == height) {
                width = tmpValue;
                height = tmpValue;
            } else if (width > height) {
                width = width * tmpValue / height;
                height = tmpValue;
            } else {
                height = height * tmpValue / width;
                width = tmpValue;
            }
        }

        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

        BitMatrix output = new BitMatrix(outputWidth, outputHeight);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            // Write the contents of this row of the barcode
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
            }
        }

        return output;
    }

    /**
     * 如果留白超过15% , 则需要缩放
     * (15% 可以根据实际需要进行修改)
     *
     * @param qrCodeSize 二维码大小
     * @param expectSize 期望输出大小
     * @return 返回缩放比例, <= 0 则表示不缩放, 否则指定缩放参数
     */
    private static int calculateScale(int qrCodeSize, int expectSize) {
        if (qrCodeSize >= expectSize) {
            return 0;
        }

        int scale = expectSize / qrCodeSize;
        int abs = expectSize - scale * qrCodeSize;
        if (abs < expectSize * 0.15) {
            return 0;
        }

        return scale;
    }

    @Override
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    @Override
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }

        if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        }

        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' +
                    height);
        }

        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int quietZone = QUIET_ZONE_SIZE;
        if (hints != null) {
            ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel) hints.get(EncodeHintType.ERROR_CORRECTION);
            if (requestedECLevel != null) {
                errorCorrectionLevel = requestedECLevel;
            }
            Integer quietZoneInt = (Integer) hints.get(EncodeHintType.MARGIN);
            if (quietZoneInt != null) {
                quietZone = quietZoneInt;
            }
        }

        QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
        return renderResult(code, width, height, quietZone);
    }
}
