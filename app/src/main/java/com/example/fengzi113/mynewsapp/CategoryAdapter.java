package com.example.fengzi113.mynewsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return new BasketballFragment();
        } else if(position ==1){
            return new FinancialFragment();
        } else if(position ==2){
            return new InternationalFragment();
        } else {
            return new TechnologyFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position ==0){
            return mContext.getString(R.string.nba);
        } else if(position ==1){
            return mContext.getString(R.string.jingji);
        } else if(position ==2){
            return mContext.getString(R.string.guoji);
        } else {
            return mContext.getString(R.string.keji);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
