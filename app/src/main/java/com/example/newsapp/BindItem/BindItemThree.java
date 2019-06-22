package com.example.newsapp.BindItem;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.NewsDetailActivity;
import com.example.newsapp.R;
import com.example.newsapp.db.Picture;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class BindItemThree {
    private View view;
    private TextView titleTextView;
    private TextView columnTextView;
    private ImageView pictureOne;
    private ImageView pictureTwo;
    private ImageView pictureThree;
    private LinearLayout itemBottom;
    private String title;
    private List<Picture> pictureList;
    private LinearLayout container;
    private int layoutType;
    private String column;
    private boolean itemBottomVisibility;
    private int news_id;
    public BindItemThree(int layoutType, LinearLayout container, String title, List<Picture> pictureList, String column, boolean itemBottomVisibility, int id){
        view=LayoutInflater.from(getContext()).inflate(layoutType,container,false);
        this.container=container;
        this.title=title;
        this.pictureList=pictureList;
        this.column=column;
        this.layoutType=layoutType;
        this.itemBottomVisibility=itemBottomVisibility;
        news_id=id;
        viewHolder();
        addView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                intent.putExtra("news_id",news_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //activity继承了context重载了startActivity方法,如果使用acitvity中的startActivity，不会有任何限制。
                // 而如果直接使用context的startActivity则会报上面的错误，根据错误提示信息,可以得知,如果要使用这种方式需要打开新的TASK。
                getContext().startActivity(intent);
            }
        });
    }
    private void viewHolder(){
        titleTextView = view.findViewById(R.id.news_title);
        pictureOne = view.findViewById(R.id.news_picture_one);
        pictureTwo = view.findViewById(R.id.news_picture_two);
        pictureThree = view.findViewById(R.id.news_picture_three);
        itemBottom=view.findViewById(R.id.news_bottom);
        columnTextView = view.findViewById(R.id.news_column);
    }
    private void addView(){
        titleTextView.setText(title);
        Glide.with(getContext()).load(pictureList.get(0).getPictureUrl()).into(pictureOne);
        Glide.with(getContext()).load(pictureList.get(1).getPictureUrl()).into(pictureTwo);
        Glide.with(getContext()).load(pictureList.get(2).getPictureUrl()).into(pictureThree);
        columnTextView.setText(column);
        itemBottom.setVisibility(View.GONE);
        if(itemBottomVisibility==true)
        {itemBottom.setVisibility(View.VISIBLE);}
        container.addView(view);
    }
}
