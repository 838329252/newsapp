package com.example.newsapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.util.Util;
import com.example.newsapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

import static org.litepal.LitePalApplication.getContext;

public class HttpUtil {
    private  static String responseData;
    public static String sendOkHttpRequest() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://192.168.43.166:3000/get/news")
                            .build();
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                    Log.d("ResponseData",responseData);
                    HandleJSON.handleNews(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
        return responseData;
    }

}
