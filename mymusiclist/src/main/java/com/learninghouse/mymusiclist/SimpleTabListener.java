package com.learninghouse.mymusiclist;

import android.R;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;


public class SimpleTabListener implements ActionBar.TabListener {
    private Context mContext;
    private String mTabFragmentClassName;
    private Fragment mTabFragment = null;

    public SimpleTabListener(Context c, String tabFragmentClassName) {
        this.mContext = c;
        this.mTabFragmentClassName = tabFragmentClassName;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (mTabFragment==null){
            mTabFragment = Fragment.instantiate(mContext, mTabFragmentClassName);
            fragmentTransaction.add(R.id.content, mTabFragment); //R.id.content is a constant for the current content
        }else{
            fragmentTransaction.attach(mTabFragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.detach(mTabFragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.detach(mTabFragment);
    }
}
