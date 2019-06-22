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
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private String userAccount;
    private String password;
    private Button registerButton;
    private EditText userAccountEdit;
    private EditText passwordEdit;
    private TextView toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userAccountEdit=(EditText)findViewById(R.id.userAccount);
        passwordEdit=(EditText)findViewById(R.id.password);
        toLogin=(TextView)findViewById(R.id.toLogin);
        registerButton=(Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccount=userAccountEdit.getText().toString();
                password=passwordEdit.getText().toString();
                List list=DataSupport.where("userAccount=? and password=?",userAccount,password).find(User.class);
                if(list.size()==0){
                    JDBC jdbc=new JDBC();
                    jdbc.InsertDataToUser("新用户",userAccount,password,R.mipmap.head);
                    GlobalData.setUserAccount(userAccount);
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this,"该账号已注册,请直接登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
