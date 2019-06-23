package com.example.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.newsapp.BindItem.ShowComment;
import com.example.newsapp.db.Comment;
import com.example.newsapp.db.GlobalData;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MyComments extends AppCompatActivity {
    private LinearLayout container;
    private Toolbar toolbar;
    private List<Comment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        container=(LinearLayout)findViewById(R.id.myComments) ;
        container.removeAllViews();
        list=DataSupport.where("user_id=?",GlobalData.getUserId()+"").find(Comment.class);
        for(Comment comment:list){
            ShowComment showComment=new ShowComment(container,comment);
        }
    }
}
