package com.example.newsapp.BindItem;

import android.content.Intent;
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

public class BindItemLarge {
    private View view;
    private TextView titleTextView;
    private TextView columnTextView;
    private ImageView pictureImageView;
    private LinearLayout itemBottom;
    private String title;
    private List<Picture> pictureList;
    private LinearLayout container;
    private int layoutType;
    private String column;
    private boolean itemBottomVisibility;
    private int news_id;
    private Intent fromLogin;
    public BindItemLarge(int layoutType, LinearLayout container, String title, List<Picture> pictureList, String column,boolean itemBottomVisibility,int id){
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("news_id",news_id);
                getContext().startActivity(intent);
            }
        });
    }
    private void viewHolder(){
        titleTextView = view.findViewById(R.id.news_title);
        pictureImageView = view.findViewById(R.id.news_picture_large);
        itemBottom=view.findViewById(R.id.news_bottom);
        columnTextView = view.findViewById(R.id.news_column);
    }
    private void addView(){
        titleTextView.setText(title);
        Glide.with(getContext()).load(pictureList.get(0).getPictureUrl()).into(pictureImageView);
        columnTextView.setText(column);
        itemBottom.setVisibility(View.GONE);
        if(itemBottomVisibility==true)
        {itemBottom.setVisibility(View.VISIBLE);}
        container.addView(view);
    }
}
