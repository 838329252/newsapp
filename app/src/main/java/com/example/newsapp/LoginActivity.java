package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private String userAccount;
    private String password;
    private Button loginButton;
    private EditText userAccountEdit;
    private EditText passwordEdit;
    private TextView toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userAccountEdit=(EditText)findViewById(R.id.userAccount);
        passwordEdit=(EditText)findViewById(R.id.password);
        toRegister=(TextView)findViewById(R.id.toRegister);
        loginButton=(Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccount=userAccountEdit.getText().toString();
                password=passwordEdit.getText().toString();
                List list=DataSupport.where("userAccount=? and password=?",userAccount,password).find(User.class);
                if(list.size()==0){
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }else {
                    GlobalData.setUserAccount(userAccount);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
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
