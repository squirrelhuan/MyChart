package com.example.administrator.mychart.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.mychart.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class MyQrCodeActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView sweepIV;
    private ImageView iv_btn_01, iv_btn_02, iv_btn_03;
    private int QR_WIDTH = 560, QR_HEIGHT = 560;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr_code);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //* toolbar.setLogo(R.drawable.ic_launcher);//设置图标
        toolbar.setTitle("我的二维码");//标题
        // toolbar.setSubtitle("Sub title");//副标题 */
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.imagebtn_back);//设置Navigation 图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();
    }

    private void initView() {
        sweepIV = (ImageView) findViewById(R.id.sweepIV);
        iv_btn_01 = (ImageView) findViewById(R.id.iv_btn_01);
        iv_btn_02 = (ImageView) findViewById(R.id.iv_btn_02);
        iv_btn_03 = (ImageView) findViewById(R.id.iv_btn_03);
        iv_btn_01.setOnClickListener(this);
        iv_btn_02.setOnClickListener(this);
        iv_btn_03.setOnClickListener(this);

        //显示到一个ImageView上面
        sweepIV.setImageBitmap(Create2DCode("http://weibo.com/3031435150/profile?topnav=1&wvr=6&is_all=1",QR_WIDTH,QR_HEIGHT));
        //createQRImage("http://weibo.com/3031435150/profile?topnav=1&wvr=6&is_all=1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                // Intent intent = new Intent(RegistActivity,)
                break;
            case R.id.btn_regist:
                break;
        }
    }

    //Edited by mythou
    //http://www.cnblogs.com/mythou/
    //要转换的地址或字符串,可以是中文
    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            // Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            // hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new MultiFormatWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            matrix = deleteWhite(matrix);//删除白边
            //图像数据转换，使用了矩阵转换
            //BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0x00000000;
                        //pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

           /* Bitmap bitmap2 = ((BitmapDrawable) getResources()
                    .getDrawable(R.drawable.mof)).getBitmap();

            Drawable[] array = new Drawable[2];
            array[0] = new BitmapDrawable(bitmap2);
            array[1] = new BitmapDrawable(bitmap);
            LayerDrawable la = new LayerDrawable(array);
            sweepIV.setImageDrawable(la);*/

            //显示到一个ImageView上面
            sweepIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap Create2DCode(String str, int width, int height) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
            matrix = deleteWhite(matrix);//删除白边
            width = matrix.getWidth();
            height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] =  0x00000000/*Color.BLACK*/;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
