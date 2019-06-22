package com.example.newsapp.BindItem;

import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.db.Comment;
import com.example.newsapp.db.User;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.zip.Inflater;

import static org.litepal.LitePalApplication.getContext;

public class ShowComment {
    private List<Comment> commentList;
    private LinearLayout container;
    private View view;
    private ImageView head;
    private TextView username;
    private TextView commentContent;
    private TextView commentTime;
    private Comment comment;
    public ShowComment(Comment comment,LinearLayout container){
        view=LayoutInflater.from(getContext()).inflate(R.layout.item_comment,container,false);
        this.comment=comment;
        this.container=container;
        viewHolder();
        addView();
    }
    public void viewHolder(){
        head=(ImageView)view.findViewById(R.id.head);
        username=(TextView)view.findViewById(R.id.username);
        commentContent=(TextView)view.findViewById(R.id.comment_content);
        commentTime=(TextView)view.findViewById(R.id.comment_time);
    }
    public void addView(){
            String content=comment.getContent();
            String time=comment.getTime();
            int user_id=comment.getUser_id();
            List<User> list=DataSupport.where("id=?",user_id+"").find(User.class);
            User user=list.get(0);
            String name=user.getUsername();
            int headUrl=user.getHeadPicture();
            head.setImageResource(headUrl);
            username.setText(name);
            commentContent.setText(content);
            commentTime.setText(time);
            container.addView(view);
    }
}
