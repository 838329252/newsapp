package com.example.newsapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.newsapp.BindItem.ShowComment;
import com.example.newsapp.BindItem.ShowNews;
import com.example.newsapp.db.Collect;
import com.example.newsapp.db.Comment;
import com.example.newsapp.db.DislikeNews;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.JDBC;
import com.example.newsapp.db.LikeNews;
import com.example.newsapp.db.News;
import com.example.newsapp.db.Relate;
import com.example.newsapp.util.NoScrollWebView;
import com.ldoublem.thumbUplib.ThumbUpView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.litepal.LitePalApplication.getContext;

public class NewsDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NoScrollWebView webView;
    private RadioButton like;
    private RadioButton dislike;
    private RadioGroup radioGroup;
    private ImageView likeImageView;
    private ImageView dislikeImageView;
    private TextView likeNumber;
    private TextView dislikeNumber;
    private TextView viewAll;
    private JDBC jdbc=new JDBC();
    private Intent intent;
    private ShowNews showNews;
    private ImageButton send;
    private EditText commentEdit;
    private int news_id;
    private int user_id;
    private ImageButton collect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        intent=getIntent();
        news_id=intent.getIntExtra("news_id",0);
        user_id=GlobalData.getUserId();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);  //不显示toolbar后边的名称

        setNewsInfo();

        webView=(NoScrollWebView) findViewById(R.id.news_content);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        initWebview();
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://daily.zhihu.com/story/9712222?utm_campaign=in_app_share&utm_medium=Android&utm_source=Weixin");
            }
        });


        setRelateStories();
        showLatestComment();
        send=(ImageButton)findViewById(R.id.sendComment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
                InputMethodManager manager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null)
                    manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        viewAll=(TextView)findViewById(R.id.viewAll) ;
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NewsDetailActivity.this,CommentAllActivity.class);
                intent.putExtra("news_id",news_id);
                startActivity(intent);
            }
        });


        likeImageView = (ImageView) findViewById(R.id.like_imageView);
        likeNumber = (TextView) findViewById(R.id.like_number);
        dislikeImageView = (ImageView) findViewById(R.id.dislike_imageView);
        dislikeNumber = (TextView) findViewById(R.id.dislike_number);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        like=(RadioButton)findViewById(R.id.like);
        dislike=(RadioButton)findViewById(R.id.dislike);
        judgeLikeOrDislike();
        likeOrdislike();


        collect=(ImageButton)findViewById(R.id.collect);
        int count=DataSupport.where("user_id=? and news_id=?",user_id+"",news_id+"").count(Collect.class);
        if(count!=0){
            collect.setImageResource(R.mipmap.colleted);
        }
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collect();
            }
        });
    }
    private void initWebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //启用应用缓存
        webSettings.setAppCacheEnabled(false);
        webSettings.setDatabaseEnabled(false);
        //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        webSettings.setDomStorageEnabled(true);
      //适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //解决android7.0以后版本加载异常问题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 这个方法在6.0才出现
               /* int statusCode = errorResponse.getStatusCode();
                if (ERROR_CODE404 == statusCode || ERROR_CODE500 == statusCode) {
                }*/
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //接受证书
                handler.proceed();
            }
        });
    }
    private void judgeLikeOrDislike(){
        int countlikeFirst=DataSupport.where("news_id=? and user_id=?",news_id+"",user_id+"").count(LikeNews.class);
        int countDislikeFirst=DataSupport.where("news_id=? and user_id=?",news_id+"",user_id+"").count(DislikeNews.class);
        if (countlikeFirst!=0){
            like.setChecked(true);
            likeImageView.setImageResource(R.mipmap.like_checked);
            int countLike=DataSupport.where("news_id=?", news_id + "").count(LikeNews.class);
            likeNumber.setText(countLike + "");
            likeNumber.setTextColor(getResources().getColor(R.color.colorColumn));
            dislike.setClickable(false);
        }
        if(countDislikeFirst!=0){
            dislike.setChecked(true);
            dislikeImageView.setImageResource(R.mipmap.dislike_checked);
            int countDislike = DataSupport.where("news_id=?", news_id + "").count(DislikeNews.class);
            dislikeNumber.setText(countDislike + "");
            dislikeNumber.setTextColor(getResources().getColor(R.color.colorColumn));
            like.setClickable(false);

        }
    }
    private void likeOrdislike(){
        likeNumber=(TextView)findViewById(R.id.like_number);
        final JDBC jdbc=new JDBC();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (like.isChecked() == true) {
                    likeImageView.setImageResource(R.mipmap.like_checked);
                    jdbc.InsertDataToLikeNews(news_id, user_id);
                    int countLike = DataSupport.where("news_id=?", news_id + "").count(LikeNews.class);
                    likeNumber.setText(countLike + "");
                    likeNumber.setTextColor(getResources().getColor(R.color.colorColumn));
                    dislike.setClickable(false);
                    //以后写数字+1的逻辑
                }
                if (dislike.isChecked() == true) {
                    dislikeImageView.setImageResource(R.mipmap.dislike_checked);
                    jdbc.InsertDataToDislikeNews(news_id, user_id);
                    int countDislike = DataSupport.where("news_id=?", news_id + "").count(DislikeNews.class);
                    dislikeNumber.setText(countDislike + "");
                    dislikeNumber.setTextColor(getResources().getColor(R.color.colorColumn));
                    like.setClickable(false);
                    //以后写数字+1的逻辑
                }
            }
        });
    }
    private  void setRelateStories(){
        List<Relate> list=new ArrayList<Relate>();
        List<News> newslist=new ArrayList<News>();
        List<News> templelist=new ArrayList<News>();
        LinearLayout relatedStories=(LinearLayout)findViewById(R.id.relatedStories);

        list=jdbc.findRelateByNewsId(news_id);
        for(Relate relate:list){
            int relateNewsId=relate.getRelate_newsId();
            templelist=jdbc.findNewsById(relateNewsId);
            if(templelist.size()!=0){
            News news=templelist.get(0);
            newslist.add(news);}
        }
  /*      Log.d("HIHInewslist",newslist+"");*/
        relatedStories.removeAllViews();
        showNews=new ShowNews(relatedStories,true,newslist);
    }
    private void showLatestComment(){
        JDBC jdbc=new JDBC();
        List<Comment> list=jdbc.findLatestComment(news_id);
        LinearLayout commentStories=(LinearLayout)findViewById(R.id.latestComment);
        commentStories.removeAllViews();
        for(Comment comment:list){
            ShowComment showComment=new ShowComment(comment,commentStories);
        }
    }
    private void sendComment(){
        commentEdit=(EditText)findViewById(R.id.comment_edit);
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        final String time=sdf.format(date);
        final String comment=commentEdit.getText().toString();
        JDBC jdbc=new JDBC();
        jdbc.InsertDataToComment(comment,news_id,user_id,time);
        showLatestComment();
        commentEdit.setText("");
    }
    private void setNewsInfo(){
        TextView newsTitle=(TextView)findViewById(R.id.news_title_detail);
        TextView author=(TextView)findViewById(R.id.news_author);
        TextView newsTime=(TextView)findViewById(R.id.news_time);
        List<News> newslist=DataSupport.where("id=?",news_id+"").find(News.class);
        News news=newslist.get(0);
        newsTitle.setText(news.getTitle());
        author.setText(news.getAuthor());
        newsTime.setText(news.getTime());
    }
    private void collect(){
        int count=DataSupport.where("user_id=? and news_id=?",user_id+"",news_id+"").count(Collect.class);
        if(count!=0){
            DataSupport.deleteAll(Collect.class,"user_id=? and news_id=?",user_id+"",news_id+"");
            collect.setImageResource(R.mipmap.collect);

        }else {
             collect.setImageResource(R.mipmap.colleted);
             jdbc.InsertDataToCollect(news_id, user_id);

        }
    }
}
