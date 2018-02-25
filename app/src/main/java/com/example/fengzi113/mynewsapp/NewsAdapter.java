package com.example.fengzi113.mynewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fengzi113 on 2018/2/5.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, ArrayList<News> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);

        }

        News currentWord = (News) getItem(position);

        TextView title = listItemView.findViewById(R.id.tv_title);
        TextView time = listItemView.findViewById(R.id.tv_time);

        title.setText(currentWord.getmTitle());
        time.setText(currentWord.getmTime());

        return listItemView;
    }
}
