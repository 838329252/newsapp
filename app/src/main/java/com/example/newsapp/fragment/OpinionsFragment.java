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


public class OpinionsFragment extends Fragment {
    private LinearLayout opinionLayout;
    private ShowNews showNews;
    private List pictureList;
    private List relatedList;
    private JDBC jdbc;
    private boolean itemBottomVisibility;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_opinions, container, false);
        opinionLayout=view.findViewById(R.id.opinionLinearLayout);
        opinionLayout.removeAllViews();
        itemBottomVisibility=false;
        showNews=new ShowNews("OPINIONS",opinionLayout,itemBottomVisibility);
        return view;
    }
}
