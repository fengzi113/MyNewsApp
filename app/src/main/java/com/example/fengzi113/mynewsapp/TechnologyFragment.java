package com.example.fengzi113.mynewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TechnologyFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String TAG = "TechnologyFragment";
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?q=Technology&api-key=test";
    private NewsAdapter mAdapter;
    private TextView emptyView;
    private View basketballView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "===========onCreate -- initLoader============");
        super.onCreate(savedInstanceState);
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        getActivity().getLoaderManager().initLoader(3, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "===========onCreateView============");
        basketballView = inflater.inflate(R.layout.list_view, container, false);
        //  指示器
        emptyView = basketballView.findViewById(R.id.empty_view);
        ListView listView = basketballView.findViewById(R.id.list);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mAdapter);

        //设置点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News new1 = mAdapter.getItem(position);
                Uri uri = null;
                if (new1 != null) {
                    uri = Uri.parse(new1.getmUrl());
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(intent);
            }
        });

        // 网络监测
        NetworkInfo networkInfo = null;
        Boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        isConnected = networkInfo != null && networkInfo.isConnected();

        if (isConnected) {
            Log.d(TAG, "=========== onCreateView -- restartLoader ============");
            //  getActivity() ---- this
            getActivity().getLoaderManager().restartLoader(3, null, this);

        } else {

            emptyView.setText(R.string.no_internet);
            ProgressBar progressBar = basketballView.findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);

        }
        return basketballView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "===========onCreateLoader============");

        return new NewsAsyncTaskLoader(getActivity(), REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> new1) {
        ProgressBar progressBar = basketballView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "===========onLoadFinished============");

        if (new1 == null) {

            emptyView.setText(R.string.no_news);
        }

        mAdapter.clear();

        if (new1 != null && !new1.isEmpty()) {
            mAdapter.addAll(new1);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        Log.d(TAG, "===========onLoaderReset============");

        mAdapter.clear();
    }
}
