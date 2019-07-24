package com.example.newsapp;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.adapter.SearchAdapter;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.News;
import com.example.newsapp.db.SearchHistory;
import com.example.newsapp.fragment.SearchRecord;
import com.example.newsapp.fragment.SearchResult;
import com.example.newsapp.util.DensityUtil;
import com.example.newsapp.util.HttpUtil;
import com.example.newsapp.util.Search;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener  {
    private SearchView searchView;
    private Search search;
    private Button button;
    private LinearLayout container;
    private RecyclerView recyclerView;
    private String searchContent;
    private int search_id;
    private TextView clearAll;
    private List<SearchHistory> searchHistoryList;
    private SearchAdapter adapter;
    private RelativeLayout empty;
    private HttpUtil httpUtil=new HttpUtil();
    private SearchResult fragment;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("seeSearchActivity","seeSearchActivity");
        DataSupport.deleteAll(SearchHistory.class);
        setContentView(R.layout.activity_search);
        searchView=(SearchView)findViewById(R.id.searchEdit);
        search=new Search(searchView);
        button=(Button)findViewById(R.id.cancle);
        button.setOnClickListener(this);
        clearAll=(TextView)findViewById(R.id.clearAll);
        clearAll.setVisibility(View.GONE);
        clearAll.setOnClickListener(this);
        container=(LinearLayout) findViewById(R.id.searchResult);
        container.removeAllViews();
        empty=(RelativeLayout)findViewById(R.id.empty);
        empty.setVisibility(View.GONE);
        recyclerView=(RecyclerView)findViewById(R.id.historySearch) ;
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUtil.sendOkHttpForSearchHistory(GlobalData.getUserId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        searchHistoryList=DataSupport.where("user_id=?",GlobalData.getUserId()+"").order("id desc").find(SearchHistory.class);
                        if(searchHistoryList.size()==0){
                            clearAll.setVisibility(View.GONE);
                        }else{
                            clearAll.setVisibility(View.VISIBLE);
                        }
                        adapter=new SearchAdapter(searchHistoryList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(MyItemClickListener);
                        /*ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
                        if (searchHistoryList.size() > 4) {
                            lp.height = DensityUtil.dip2px(SearchActivity.this,50 * 4);
                        } else {
                            lp.height = DensityUtil.dip2px(SearchActivity.this,50 * searchHistoryList.size());
                        }
                        recyclerView.setLayoutParams(lp);*/
                    }
                });
            }
        }).start();
        setListener();
        closeListener();
    }

    private void setListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContent=query;
                List<News> list=getNewsList(query);
                if(list.size()!=0){
                    ShowNews showNews=new ShowNews(container,true,list);
                }else{
                    /*Toast.makeText(SearchActivity.this,"没有搜索到结果",Toast.LENGTH_SHORT).show();*/
                    clearAll.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                searchView.setQuery(searchContent, false);
                searchView.clearFocus();  //可以收起键盘
                recyclerView.setVisibility(View.GONE);
                clearAll.setVisibility(View.GONE);
                updateHistory();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    private void updateHistory(){
        SearchHistory searchHistory=new SearchHistory();
        searchHistory.setSearch(searchContent);
        searchHistory.setUser_id(GlobalData.getUserId());
        searchHistory.save();
        searchHistoryList=DataSupport.where("user_id=?",GlobalData.getUserId()+"").order("id desc").find(SearchHistory.class);
        adapter=new SearchAdapter(searchHistoryList);
        recyclerView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUtil.sendOkHttpUpdateSearchHistory(GlobalData.getUserId(),searchContent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setOnItemClickListener(MyItemClickListener);
                    }
                });
            }
        }).start();
    }
    private SearchAdapter.OnItemClickListener MyItemClickListener = new SearchAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, SearchAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.deleteRecord:
                    SearchHistory searchHistory=searchHistoryList.get(position);
                    search_id=searchHistory.getSearch_id();
                    DataSupport.deleteAll(SearchHistory.class,"search_id=?",search_id+"");
                    searchHistoryList=DataSupport.where("user_id=?",GlobalData.getUserId()+"").order("id desc").find(SearchHistory.class);
                    if(searchHistoryList.size()==0){
                        clearAll.setVisibility(View.GONE);
                    }
                    SearchAdapter adapter=new SearchAdapter(searchHistoryList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MyItemClickListener);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            httpUtil.sendOkHttpTodeleteSearchHistory(search_id);
                        }
                    }).start();
                    break;
                default:
                    SearchHistory searchHistory1=searchHistoryList.get(position);
                    searchContent=searchHistory1.getSearch();
                    List<News> list=getNewsList(searchContent);
                    if(list.size()!=0){
                        ShowNews showNews=new ShowNews(container,true,list);
                    }else{
                        /*Toast.makeText(SearchActivity.this,"没有搜索到结果",Toast.LENGTH_SHORT).show();*/
                        empty.setVisibility(View.VISIBLE);
                    }
                    searchView.setQuery(searchContent, false);
                    searchView.clearFocus();  //可以收起键盘
                    recyclerView.setVisibility(View.GONE);
                    clearAll.setVisibility(View.GONE);
                    break;
            }
        }

        public void onItemLongClick(View v) {
        }
    };
    private void closeListener(){
        ImageView mCloseButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        if (mCloseButton.isClickable()) {
            mCloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //清除搜索框并加载默认数据
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    recyclerView.setVisibility(View.VISIBLE);
                    clearAll.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    container.removeAllViews();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancle:
                finish();break;
            case R.id.clearAll:
                DataSupport.deleteAll(SearchHistory.class);
                searchHistoryList=DataSupport.where("user_id=?",GlobalData.getUserId()+"").order("id desc").find(SearchHistory.class);
                if(searchHistoryList.size()==0){
                    clearAll.setVisibility(View.GONE);
                }
                adapter=new SearchAdapter(searchHistoryList);
                recyclerView.setAdapter(adapter);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpUtil.sendOkHttpToclearSearchHistory();
                    }
                }).start();break;

        }
    }
    public List getNewsList(String content){
        List <News> list=DataSupport.findAll(News.class);
        List <News> addList=new ArrayList<News>();
        for(News news:list){
            String titie=news.getTitle();
            if(titie.toLowerCase().contains(content.toLowerCase())){
                addList.add(news);
            }

        }
        return addList;
    }
}
