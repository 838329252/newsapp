package com.example.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.adapter.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTabNames;
    private List<Fragment> fragments;
    private TabPageAdapter pageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_tab, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.middle_viewPager);
        fragments=new ArrayList<>();
        //将提前写好三个Fragment添加到集合中
        fragments.add(new TopNewsFragment());
        fragments.add(new BusinessFragment());
        fragments.add(new OpinionsFragment());
        fragments.add(new CultureFragment());
        //创建适配器

        //设置ViewPager的适配器
        
        mTabNames=new String[]{"TOP NEWS","BUSINESS","OPINIONS","CULTURE"};
        pageAdapter = new TabPageAdapter(getChildFragmentManager(), fragments,mTabNames);  //getChildFragmentManager()是在fragment 里面子容器的碎片管理。
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
        return view;
    }
}
