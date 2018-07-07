package com.zzy.zxing.ac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.zzy.event.ac.R;
import com.zzy.zxing.tools.QRCode;

public class ZXingActivity extends AppCompatActivity {
    private static final String TAG = "ZXingActivity";
    private ImageView iv1,iv2,iv3,iv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        int wight = (int) getResources().getDimension(R.dimen.zxing);
        try {
            QRCode qrCode = new QRCode();
            String url = "num=TBCL03&time=20180705152345";
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            iv1.setImageBitmap(qrCode.getQRCode(url, wight, wight,0));
            iv2.setImageBitmap(qrCode.getQRCode(url, wight, wight,1));
            iv3.setImageBitmap(qrCode.getQRCode(url, wight, wight,3));
            iv4.setImageBitmap(qrCode.getQRCode(url, wight, wight,5));


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



}
