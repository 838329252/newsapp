package com.example.newsapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;

import java.util.ArrayList;

public class ImgAdapter extends PagerAdapter {
    private ArrayList<PhotoView> imageViews;

    public ImgAdapter(ArrayList<PhotoView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int i = position % imageViews.size();
        //根据条目所在位子,从imageviews集合中获取相对应的图片
        PhotoView imageView = imageViews.get(i);
        //把得到的imageview对象,添加到viewpager,也就是container
        ViewPager parentViewGroup = (ViewPager)imageView.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeView(imageView);
        }
        ((ViewPager) container).addView(imageView);

        return imageView;
    }

    //要销毁,防止内存泄漏
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }
}
