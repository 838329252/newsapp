package com.example.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newsapp.BindItem.BindItemLarge;
import com.example.newsapp.BindItem.BindItemThree;
import com.example.newsapp.BindItem.BindItemTwo;
import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment {
    private JDBC jdbc;
    private List businessNewsList;
    private List pictureList;
    private List relatedList;
    private LinearLayout businessLayout;
    private ShowNews showNews;
    private boolean itemBottomVisibility;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_business, container, false);
        businessLayout=view.findViewById(R.id.businessLinearLayout);
        businessLayout.removeAllViews();
        itemBottomVisibility=false;
        showNews=new ShowNews("BUSINESS",businessLayout,itemBottomVisibility);
         return view;
    }
}

