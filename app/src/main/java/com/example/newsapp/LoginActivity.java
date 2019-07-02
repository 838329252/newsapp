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
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                password=passwordEdit.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        count=httpUtil.sendOkHttpForLogin(userAccount,password);
                        runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                //更新UI
                                if(count==0){
                                    Toast.makeText(getContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                                }else {
                                    GlobalData.setUserAccount(userAccount);
                                   Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                   startActivity(intent);
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
