package com.example.newsapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.newsapp.LoginActivity;
import com.example.newsapp.R;
import com.example.newsapp.RegisterActivity;

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
    private String responseData;
    private String httpUrl;
    private String userAccount;
    private String password;
    private int count;
    private String userData;
    private int news_id;
    private int user_id;
    public  String sendOkHttpRequest(String url) {
        httpUrl=url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://10.128.231.97:3000/get/"+httpUrl)
                            .build();
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                    switch(httpUrl){
                        case "all":
                            HandleJSON.handleNewsPictureRelate(responseData);break;
                        case "login":
                            break;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
        return responseData;
    }
    public int sendOkHttpForNewsDetail(int newsId,int userId,String url){
        int count=0;
        news_id=newsId;
        user_id=userId;
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("newsId",news_id+"")
                    .add("userId",user_id+"")
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/"+ url)
                    .post(requestBody)
                    .build();
            Response response=client.newCall(request).execute();
            responseData=response.body().string();
            switch(url){
                case "newsDetail":
                    HandleJSON.handleNewsDetail(responseData);break;
                case "insertLike":break;

                case "insertDislike":break;
                case "insertCollect":break;
                case "deleteCollect":break;
                case "countCollect":
                    count=HandleJSON.handleCount(responseData);break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public void sendOkHttpForComment(int newsId,int userId,String content,String time){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("newsId",newsId+"")
                    .add("userId",userId+"")
                    .add("content",content)
                    .add("time",time)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/insertComment")
                    .post(requestBody)
                    .build();
            Response response=client.newCall(request).execute();
            responseData=response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int sendOkHttpForLogin(String userAccounts, String passwords){
        userAccount=userAccounts;
        password=passwords;
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userAccount",userAccount)
                    .add("password",password)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/login")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            count=HandleJSON.handleLogin(responseData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public  String sendOkHttpForRegister(String userAccounts, String passwords){
        userAccount=userAccounts;
        password=passwords;
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userAccount",userAccount)
                    .add("password",password)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/register")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            userData = response.body().string();
            JSONArray jsonArray=new JSONArray(userData);
            HandleJSON.handleUserInfo(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }
    public  void sendOkHttpForMy(int user_id,String url){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userId",user_id+"")
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/"+url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
            switch (url){
                case "comment":HandleJSON.handleComment(responseData);break;
                case "collect":HandleJSON.handleCollect(responseData);break;
                case "user":
                    JSONArray jsonArray=new JSONArray(responseData);
                    HandleJSON.handleUserInfo(jsonArray);break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void sendOkHttpToChangeHead(int user_id,String headUrl){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userId",user_id+"")
                    .add("head",headUrl)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/updateHead")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void sendOkHttpToChangeUsername(int user_id,String username){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userId",user_id+"")
                    .add("username",username)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/updateUsername")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void sendOkHttpToChangePassword(int user_id,String password){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userId",user_id+"")
                    .add("password",password)
                    .build();
            Request request=new Request.Builder()
                    .url("http://10.128.231.97:3000/get/updatePassword")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
