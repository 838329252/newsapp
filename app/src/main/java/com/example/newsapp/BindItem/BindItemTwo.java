package com.example.newsapp.BindItem;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class BindItemTwo {
    private View view;
    private TextView titleTextView;
    private TextView columnTextView;
    private ImageView pictureLeft;
    private ImageView pictureRight;
    private LinearLayout itemBottom;
    private String title;
    private List pictureList;
    private LinearLayout container;
    private int layoutType;
    private String column;
    private boolean itemBottomVisibility;
    public BindItemTwo(int layoutType, LinearLayout container, String title, List pictureList, String column,boolean itemBottomVisibility){
        view=LayoutInflater.from(getContext()).inflate(layoutType,container,false);
        this.container=container;
        this.title=title;
        this.pictureList=pictureList;
        this.column=column;
        this.layoutType=layoutType;
        this.itemBottomVisibility=itemBottomVisibility;
        viewHolder();
        addView();

    }
    private void viewHolder(){
        titleTextView = view.findViewById(R.id.news_title);
        pictureLeft = view.findViewById(R.id.news_picture_left);
        pictureRight = view.findViewById(R.id.news_picture_right);
        itemBottom=view.findViewById(R.id.news_bottom);
        columnTextView = view.findViewById(R.id.news_column);
    }
    private void addView(){
        titleTextView.setText(title);
        Glide.with(getContext()).load(pictureList.get(0)).into(pictureLeft);
        Glide.with(getContext()).load(pictureList.get(1)).into(pictureRight);
        columnTextView.setText(column);
        itemBottom.setVisibility(View.GONE);
        if(itemBottomVisibility==true)
        {itemBottom.setVisibility(View.VISIBLE);}
        container.addView(view);
    }
}
