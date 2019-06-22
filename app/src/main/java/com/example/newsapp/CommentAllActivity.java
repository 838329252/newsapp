package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.example.newsapp.BindItem.ShowComment;
import com.example.newsapp.db.Comment;
import com.example.newsapp.db.JDBC;

import java.util.List;

public class CommentAllActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_all);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        JDBC jdbc=new JDBC();
        Intent intent=getIntent();
        int news_id=intent.getIntExtra("news_id",0);
        List<Comment> list=jdbc.findCommentBynewsId(news_id);
        LinearLayout commentStories=(LinearLayout)findViewById(R.id.all_comment);
        commentStories.removeAllViews();
        for(Comment comment:list){
            ShowComment showComment=new ShowComment(comment,commentStories);
        }
    }
}
