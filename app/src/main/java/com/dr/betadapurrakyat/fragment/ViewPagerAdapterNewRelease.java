package com.dr.betadapurrakyat.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dr.betadapurrakyat.Model.NewMenu;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapterNewRelease extends FragmentPagerAdapter {
    private List <Fragment> fragmentList = new ArrayList<>();
    private List <String> fragmentTitleList = new ArrayList<>();

    public void addfragment (Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }


    public ViewPagerAdapterNewRelease(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
