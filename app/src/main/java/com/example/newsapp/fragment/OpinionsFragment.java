package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.db.JDBC;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class OpinionsFragment extends Fragment {
    private LinearLayout opinionLayout;
    private ShowNews showNews;
    private List pictureList;
    private List relatedList;
    private JDBC jdbc;
    private boolean itemBottomVisibility;
    private TextView fakeSearchView;
    private RefreshLayout refreshLayout;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_opinions, container, false);
        opinionLayout=view.findViewById(R.id.opinionLinearLayout);
        opinionLayout.removeAllViews();
        fakeSearchView=(TextView)view.findViewById(R.id.fake_searchEdit) ;
        itemBottomVisibility=false;
        showNews=new ShowNews("OPINIONS",opinionLayout,itemBottomVisibility);
        fakeSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchActivity.class);
                getContext().startActivity(intent);
            }
        });

        setPullRefresher();
        return view;
    }
    private void setPullRefresher(){
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
