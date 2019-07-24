package com.example.newsapp.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;
import com.example.newsapp.db.JDBC;

import org.litepal.crud.DataSupport;

public class GlobalData extends DataSupport {
    private static String userAccount;
    private static int videoCurposition;
    public static int getUserId() {
        List<User> list=DataSupport.where("userAccount=?",userAccount).find(User.class);
        int user_id=list.get(0).getUser_id();
        return user_id;
    }

    public static String getUserAccount() {
        return userAccount;
    }

    public static void setUserAccount(String userAccount) {
        GlobalData.userAccount = userAccount;
    }

    public static int getVideoCurposition() {
        return videoCurposition;
    }

    public static void setVideoCurposition(int videoCurposition) {
        GlobalData.videoCurposition = videoCurposition;
    }
}
