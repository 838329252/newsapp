package com.example.newsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.newsapp.adapter.ImgAdapter;
import com.example.newsapp.adapter.ScrollAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import static org.litepal.LitePalApplication.getContext;

public class ViewPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager pager;
    private String[] imgUrls;
    private String curImg;
    private Intent intent;
    private TextView curPosition;
    private TextView totalCount;
    private ImageView download;
    private int position;
    private String url;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
        imgUrls=intent.getStringArrayExtra("imageUrls");
        curImg=intent.getStringExtra("curImg");
        curPosition=(TextView)findViewById(R.id.curPosition);
        totalCount=(TextView)findViewById(R.id.totalCount);
        download=(ImageView)findViewById(R.id.download);
        totalCount.setText(imgUrls.length+"");
        download.setOnClickListener(this);
        init();
    }
    private void init() {
        pager = (ViewPager) findViewById(R.id.viewPager);
        //在addView之前加入如下代码
        //photoView是缩放插件
        final ArrayList<PhotoView> imageViews = new ArrayList<>();
        /*ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(curImg).into(imageView);
        imgUrlForDown[0]=curImg;
        imageViews.add(imageView);*/
        for (int i = 0; i < imgUrls.length; i++) {
            if(imgUrls[i].equals(curImg)) {
                position=i;
            }
            //初始化imageview
            PhotoView imageView = new  PhotoView(this);
            imageView.enable();
            //把图片资源添加到imageview对象中去
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(getContext()).load(imgUrls[i])
                    .fitCenter()//缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示
                    .into(imageView);
            //把已经设置好的对象添加到集合中去
            imageViews.add(imageView);
        }
        pager.setAdapter(new ImgAdapter(imageViews));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int i, float v, int i1) {
                //通过getcurrentitem方法得到当前用户所交互的viewpager的item
                int newposition = pager.getCurrentItem() % imageViews.size();
                //通过的到这个item给text和点设置选中的状态
                changeText(newposition);
            }

            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pager.setCurrentItem(position);
    }
    private void changeText(int position){
        curPosition.setText(position+1+"");
    }
    private void setDownload(int position){
        url=imgUrls[position];
        if (ContextCompat.checkSelfPermission(ViewPhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViewPhotoActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            Toast.makeText(ViewPhotoActivity.this,"you denied the permission",Toast.LENGTH_SHORT).show();
        } else {
            bitmap1=returnBitMap(url);
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.download:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setDownload(pager.getCurrentItem());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startDownload(bitmap1);
                                if(bitmap1!=null){
                                    Toast.makeText(ViewPhotoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ViewPhotoActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
                break;
        }
    }
    public Bitmap returnBitMap(final String url){
        URL imageurl = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public void startDownload(Bitmap bitmap){
        String state = Environment.getExternalStorageState();
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MicroMsg/WeiXin/";
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(ViewPhotoActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
        }
        String fileName= UUID.randomUUID().toString();
        try {
            File file = new File(dir + fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Uri uri = Uri.fromFile(file);
            //发送广播刷新图片
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
