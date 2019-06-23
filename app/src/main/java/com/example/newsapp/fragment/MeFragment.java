package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newsapp.MyComments;
import com.example.newsapp.MyFavorites;
import com.example.newsapp.R;
import com.example.newsapp.Settings;

public class MeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout myFavorites;
    private LinearLayout myComments;
    private LinearLayout mySetting;
    private Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me, container, false);
        // Inflate the layout for this fragment
        myFavorites=(LinearLayout)view.findViewById(R.id.my_favorites);
        myComments=(LinearLayout)view.findViewById(R.id.my_comments);
        mySetting=(LinearLayout)view.findViewById(R.id.my_setting);
        myFavorites.setOnClickListener(this);
        myComments.setOnClickListener(this);
        mySetting.setOnClickListener(this);
        return view;

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.my_favorites:
                intent=new Intent(getContext(),MyFavorites.class);
                startActivity(intent); break;
            case R.id.my_comments:
                intent=new Intent(getContext(),MyComments.class);
                startActivity(intent); break;
            case R.id.my_setting:
                intent=new Intent(getContext(),Settings.class);
                startActivity(intent); break;
        }
    }
}
