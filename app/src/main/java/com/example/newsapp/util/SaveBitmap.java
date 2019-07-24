package com.example.newsapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.litepal.LitePalApplication.getContext;

public class SaveBitmap {
    public static void SaveBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
//第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userHead", imageString);
        editor.commit();
        Log.d("userHead","if save bitmap executed");
    }
    public static Bitmap getBitmap(SharedPreferences sharedPreferences){
    //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("userHead", "");
    //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray= Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
    //第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);
        Log.d("userHead","if get bitmap is executed");
        return bitmap;
    }
}
