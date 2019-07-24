package com.example.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.db.News;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SearchResult extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_search_result, container, false);

        return view;
    }

}
