package com.lzz.studydemo.ui.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limou on 2017/8/8/16:22.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<CharSequence> mTitls = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 普通首页使用
     *
     * @param fragment
     */
    public void addFramgent(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void addAllFramgent(List<Fragment> fragment) {
        mFragments.addAll(fragment);
    }

    public void addFramgent(Fragment fragment, CharSequence title) {
        mFragments.add(fragment);
        mTitls.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitls.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
