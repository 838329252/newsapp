package com.example.newsapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.newsapp.adapter.TabPageAdapter;
import com.example.newsapp.db.News;
import com.example.newsapp.fragment.BusinessFragment;
import com.example.newsapp.fragment.MeFragment;
import com.example.newsapp.fragment.TabFragment;
import com.example.newsapp.util.HttpUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTabNames;
    private List<Fragment> fragments;
    private TabPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.bottomTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.middle_viewPager_forBottom);
        fragments=new ArrayList<>();
        fragments.add(new TabFragment());
        fragments.add(new MeFragment());
        mTabNames=new String[]{"NEWS","ME"};
        pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments,mTabNames);
        mViewPager.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab项选中状态时执行
                mViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab项取消选中状态时执行
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab项选中状态再次点击时执行

            }
        });



    }

}
