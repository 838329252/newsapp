package com.example.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.adapter.SearchAdapter;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.SearchHistory;
import com.example.newsapp.util.DensityUtil;
import com.example.newsapp.util.HttpUtil;
import com.example.newsapp.util.Search;

import org.litepal.crud.DataSupport;

import java.util.List;


public class SearchRecord extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search_record, container, false);
        Log.d("seeSearchRecord","seeSearchRecord");

        return view;
    }

}
