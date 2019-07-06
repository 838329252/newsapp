package com.example.newsapp.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.newsapp.ViewPhotoActivity;

public class MJavascriptInterface {
    private Activity context;
    private static final String TAG="SIMON";
    public MJavascriptInterface(Activity context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface //必须添加
    public void openImage(String img, String[] array) {
        Log.i(TAG, "openImage: "+img);
        for (String imgUrl:array){
            Log.i(TAG, "openImage: "+imgUrl);
        }
        Intent intent = new Intent(context, ViewPhotoActivity.class);
        intent.putExtra("imageUrls", array);
        intent.putExtra("curImg", img);
        context.startActivity(intent);
    }
}