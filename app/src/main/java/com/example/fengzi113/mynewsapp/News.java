package com.example.fengzi113.mynewsapp;

public class News {
    private String mTitle;
    private String mTime;
    private String mUrl;

    public News(String title, String time, String url){
        mTime = time;
        mTitle = title;
        mUrl = url;
    }

    public String getmTitle(){
        return mTitle;
    }

    public String getmTime(){
        return mTime;
    }

    public String getmUrl(){
        return mUrl;
    }
}
