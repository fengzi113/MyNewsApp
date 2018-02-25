package com.example.fengzi113.mynewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.fengzi113.mynewsapp.News;
import com.example.fengzi113.mynewsapp.QueryUtils;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class NewsAsyncTaskLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;
    public NewsAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<News> loadInBackground() {

        if (mUrl ==null){
        return null;
        }

        return QueryUtils.fetchBookDate(mUrl);

    }

}