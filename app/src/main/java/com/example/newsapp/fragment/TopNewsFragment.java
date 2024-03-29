package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.NewsDetailActivity;
import com.example.newsapp.R;
import com.example.newsapp.SearchActivity;
import com.example.newsapp.adapter.ScrollAdapter;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.News;
import com.example.newsapp.db.Picture;
import com.example.newsapp.util.Search;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class TopNewsFragment extends Fragment {
    private LinearLayout topLinearLayout;
    private ShowNews showNews;
    private View view;
    private boolean itemBottomVisibility;
    private JDBC jdbc=new JDBC();
    private ViewPager pager;
    private TextView textView;
    private LinearLayout ll_dot;
    private TextView fakeSearchView;
    private List<News> newslist;
    private RefreshLayout refreshLayout;
    private String[] imageResIds=new String[4];
    private String[] descs=new String[4];
    private int[] newsId=new int[4];
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                    handler.sendEmptyMessageDelayed(0, 5000);

                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_top_news, container, false);
        topLinearLayout=(LinearLayout)view.findViewById(R.id.topLinearLayout);
        fakeSearchView=(TextView)view.findViewById(R.id.fake_searchEdit) ;
        topLinearLayout.removeAllViews();
        itemBottomVisibility=true;

        showNews=new ShowNews("OPINIONS",topLinearLayout,itemBottomVisibility);
        newslist=showNews.searchNewsInfoForTopNews();
        int i=0;
        if(newslist.size()!=0){
            for(News news:newslist){
                int news_id=news.getNews_id();
                newsId[i]=news_id;
                List<Picture> list=DataSupport.where("news_id=?",news_id+"").find(Picture.class);
                if (list.size()!=0){
                    imageResIds[i]=list.get(0).getPictureUrl();
                }else imageResIds[i]=R.drawable.picture_3+"";
                descs[i]=news.getTitle();
                i++;
            }
            init();
        }else{
            for(i=0;i<4;i++){
                newsId[i]=i;
                imageResIds[i]=R.drawable.picture_3+"";
                descs[i]=i+"";
            }
        }


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

    private void init() {
        pager = (ViewPager) view.findViewById(R.id.viewpager);
        textView = (TextView) view.findViewById(R.id.scrollText);
        //找到可以设置点的容器LinearLayout的对象,进行点的添加
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        final ArrayList<ImageView> imageViews = new ArrayList<>();   //一.创建一个imageview集合
        //一.创建一个集合
        for (int i=0; i < imageResIds.length; i++) {
            //初始化imageview
            ImageView imageView = new ImageView(getContext());
            //把图片资源添加到imageview对象中去
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setClickable(true);
            final int news_id=newsId[i];
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                    intent.putExtra("news_id",news_id);
                    startActivity(intent);
                }
            });
            Glide.with(getContext()).load(imageResIds[i]).into(imageView);
            //把已经设置好的对象添加到集合中去
            imageViews.add(imageView);

            //二.添加点,根据图片的多少添加多少点
            initDot();
        }
            //一.配置适配器
            pager.setAdapter(new ScrollAdapter(imageViews));

            //三.给viewpager设置监听,让小原点跟图片同步,set的方法因为名字的原因被淘汰了
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    //通过getcurrentitem方法得到当前用户所交互的viewpager的item
                    int newposition = pager.getCurrentItem() % imageViews.size();
                    //通过的到这个item给text和点设置选中的状态
                    changeTextandDot(newposition);
                }

                @Override
                public void onPageSelected(int i) {

                }

                //和用户交互时,state就是当前viewpager的状态
                @Override
                public void onPageScrollStateChanged(int i) {
                 if (i == ViewPager.SCROLL_STATE_IDLE) {
                    handler.sendEmptyMessageDelayed(0, 5000);
                    }else{//不是空闲状态,不让viewpager自行滑动
                    handler.removeMessages(0);
               }
                }
            });

            handler.sendEmptyMessage(0);

            //通过viewpager的touchEvent
            pager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeMessages(0);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            handler.removeMessages(0);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(0, 5000);
                            break;
                    }
                    return false;
                }
            });
    }
    //二.创建点
    //根据viewpager的item的变化,随着图片的切换,文本和点也进行变化
    private void initDot() {
        //创建一个view
        View view = new View(getContext());
        //为这个view设置背景setBackgroundResource
        view.setBackgroundResource(R.drawable.p_dot_normal);
        //为view对象设置宽高,使用参数对象LayoutParams
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
        //使用参数对象LayoutParams.leftMargin,想到与padding
        layoutParams.leftMargin = 4;
        //把设置好的layoutParams参数,设置给view
        view.setLayoutParams(layoutParams);
        //添加到控件
        ll_dot.addView(view);
    }
    //根据viewpager的item的变化,随着图片的切换,文本和点也进行变化
     private void changeTextandDot(int position)
     {
         //根据viewpager的item,设置相对应的text文本
         textView.setText(descs[position]);
         final int news_id=newsId[position];
         textView.setClickable(true);
         textView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                 intent.putExtra("news_id",news_id);
                 startActivity(intent);
             }
         });
         //对点进行判断,拿到点的位置判断与position是否一样
         for (int i = 0; i < imageResIds.length; i++)
         {
             View childAt = ll_dot.getChildAt(i);
             childAt.setBackgroundResource(i == position ? R.drawable.p_dot_focus : R.drawable.p_dot_normal);
         }
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
