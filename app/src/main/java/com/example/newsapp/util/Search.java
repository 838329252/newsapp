package com.example.newsapp.util;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.newsapp.R;

import java.lang.reflect.Field;

public class Search {
        private SearchView searchView;
        private AutoCompleteTextView mAutoCompleteTextView;//搜索输入框
        private ImageView mDeleteButton;//搜索框中的删除按钮
        private View view;

        public Search (SearchView searchView) {
            this.searchView=searchView;
            initView();
            initData();
            setListener();
        }

        private void initView(){
            mAutoCompleteTextView=searchView.findViewById(R.id.search_src_text);
            mDeleteButton=searchView.findViewById(R.id.search_close_btn);
        }

        private void initData(){
            searchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
            //1:回车
            //2:前往
            //3:搜索
            //4:发送
            //5:下一項
            //6:完成
            searchView.setImeOptions(2);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
//        mSearchView.setInputType(1);//设置输入类型
//        mSearchView.setMaxWidth(200);//设置最大宽度
            searchView.setQueryHint("Search People's daily");//设置查询提示字符串
//        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
            //设置SearchView下划线透明
            setUnderLinetransparent(searchView);
        }

        private void setListener(){

            // 设置搜索文本监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //当点击搜索按钮时触发该方法
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                //当搜索内容改变时触发该方法
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        /**设置SearchView下划线透明**/
        private void setUnderLinetransparent(SearchView searchView){
            try {
                Class<?> argClass = searchView.getClass();
                // mSearchPlate是SearchView父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                ownField.setAccessible(true);
                View mView = (View) ownField.get(searchView);
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

