package com.example.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.db.JDBC;

import java.util.ArrayList;
import java.util.List;

public class CultureFragment extends Fragment {
    private JDBC jdbc;
    private LinearLayout cultureLayout;
    private ShowNews showNews;
    private List pictureList;
    private List relatedList;
    private boolean itemBottomVisibility;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_culture, container, false);
        cultureLayout=view.findViewById(R.id.cultureLinearLayout);
        cultureLayout.removeAllViews();
        itemBottomVisibility=false;
        showNews=new ShowNews("CULTURE",cultureLayout,itemBottomVisibility);
        return view;
    }
}
