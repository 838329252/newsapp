package com.example.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.User;

import com.example.newsapp.util.HttpUtil;
import com.example.newsapp.util.MD5Util;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;


public class LoginActivity extends AppCompatActivity {
    private String userAccount;
    private String password;
    private Button loginButton;
    private EditText userAccountEdit;
    private EditText passwordEdit;
    private TextView toRegister;
    private String responseData;
    private HttpUtil httpUtil;
    private MD5Util md5Util;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        md5Util=new MD5Util();
        userAccountEdit=(EditText)findViewById(R.id.userAccount);
        passwordEdit=(EditText)findViewById(R.id.password);
        toRegister=(TextView)findViewById(R.id.toRegister);
        loginButton=(Button)findViewById(R.id.login_button);
        httpUtil=new HttpUtil();
        JDBC.deleteAll();
        httpUtil.sendOkHttpRequest("all");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccount=userAccountEdit.getText().toString();
                password=md5Util.encrypt(passwordEdit.getText().toString());
                if(userAccount.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"账号或密码不为空",Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtil.sendOkHttpForLogin(userAccount,password);
                        runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                //更新UI
                                GlobalData.setUserAccount(userAccount);
                                List<User> userlist=DataSupport.where("userAccount=?",userAccount).find(User.class);
                                if (userlist.size()==0){
                                    Toast.makeText(getContext(),"账号不存在请先注册",Toast.LENGTH_SHORT).show();
                                }else if(userlist.get(0).getPassword().equals(password)) {
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getContext(),"密码不正确请重新输入",Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                }).start();
            }

        });
       toRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(intent);
           }
       });

    }
}
