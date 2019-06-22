package com.example.newsapp.db;

import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;
import com.example.newsapp.db.JDBC;

import org.litepal.crud.DataSupport;

public class GlobalData extends DataSupport {
    private static String userAccount;

    public static int getUserId() {
        List<User> list=DataSupport.where("userAccount=?",userAccount).find(User.class);
        int user_id=list.get(0).getId();
        return user_id;
    }

    public static void setUserAccount(String userAccount) {
        GlobalData.userAccount = userAccount;
    }
}
