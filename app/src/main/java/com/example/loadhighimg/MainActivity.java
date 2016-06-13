package com.example.loadhighimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    public static int Device_height;
    public static int Device_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv_img);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Point point = new Point();
        //兼容老版本的Android系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(point);
            Device_width = point.x;
            Device_height = point.y;
        }else{
            Device_width =  wm.getDefaultDisplay().getWidth();
            Device_height = wm.getDefaultDisplay().getHeight();
        }
    }

    public void click(View view){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这样设置后，不是真的解析图片，只是获取图片的信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("/sdcard/a.jpg", options);
        //图片的实际宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        System.out.println("图片的实际宽度为：" + outWidth);
        System.out.println("图片的实际高度为：" + outHeight);

        //计算宽高比率，按照比较大的比较缩放
        int scaleX = outWidth / Device_width;
        int scaleY = outHeight / Device_height;
        int scale = 1;
        if(scaleX > scaleY && scaleX > 1)
            scale = scaleX;
        if(scaleY > scaleX  && scaleY > 1)
            scale = scaleY;

        //这样设置后，就会去sdcard找到图片，加载到内存
        options.inJustDecodeBounds = false;
        //设置缩放的比率
        options.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/a.jpg", options);
        iv.setImageBitmap(bitmap);
    }
}
