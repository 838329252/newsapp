package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapp.MyComments;
import com.example.newsapp.MyFavorites;
import com.example.newsapp.R;
import com.example.newsapp.Settings;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.User;
import com.example.newsapp.util.HttpUtil;
import com.example.newsapp.util.SaveBitmap;
import com.example.newsapp.util.UrlToBitmap;

import org.litepal.crud.DataSupport;

import javax.xml.transform.Result;

import static org.litepal.LitePalApplication.getContext;

public class MeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout myFavorites;
    private LinearLayout myComments;
    private LinearLayout mySetting;
    private ImageView userHead;
    private TextView account;
    private TextView username;
    private Intent intent;
    private String url;
    private Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me, container, false);
        // Inflate the layout for this fragment
        myFavorites=(LinearLayout)view.findViewById(R.id.my_favorites);
        myComments=(LinearLayout)view.findViewById(R.id.my_comments);
        mySetting=(LinearLayout)view.findViewById(R.id.my_setting);
        userHead=(ImageView)view.findViewById(R.id.userHead);
        username=(TextView)view.findViewById(R.id.usernameForMe);
        account=(TextView)view.findViewById(R.id.accountForMe);

        myFavorites.setOnClickListener(this);
        myComments.setOnClickListener(this);
        mySetting.setOnClickListener(this);



        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        User user=DataSupport.where("user_id=?",GlobalData.getUserId()+"").findFirst(User.class);
        if(user.getHeadPicture().equals("")){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
            userHead.setImageBitmap(bitmap);
        }else{
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("testSP", Context.MODE_PRIVATE);
            if(sharedPreferences!=null){
                bitmap=SaveBitmap.getBitmap(sharedPreferences);
                userHead.setImageBitmap(bitmap);
            }else{
                url="http://192.168.43.166:3000/headPicture/"+user.getHeadPicture();
                MyTask mTask = new MyTask();
                mTask.execute();
            }

        }
        username.setText(user.getUsername());
        account.setText(user.getUserAccount());
    }

    @Override

    public void onClick(View v){
        switch(v.getId()){
            case R.id.my_favorites:
                intent=new Intent(getContext(),MyFavorites.class);
                startActivity(intent); break;
            case R.id.my_comments:
                intent=new Intent(getContext(),MyComments.class);
                startActivity(intent); break;
            case R.id.my_setting:
                intent=new Intent(getContext(),Settings.class);
                startActivity(intent); break;
        }
    }
    private class MyTask extends AsyncTask<Void,Integer, Boolean> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean doInBackground(Void... params) {

            try{
                bitmap=UrlToBitmap.UrlToBitmap(url);
                SaveBitmap.SaveBitmap(bitmap);
                publishProgress();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        // 注：必须复写，从而自定义UI操作
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                userHead.setImageBitmap(bitmap);
            }

        }
    }

}

