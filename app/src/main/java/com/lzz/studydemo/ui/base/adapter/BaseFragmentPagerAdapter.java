package com.lzz.studydemo.ui.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limou on 2017/8/8/16:22.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitls = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private Fragment mCurrentFragment;

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

    public void setFragments(List<Fragment> fragment) {
        mFragments.clear();
        mFragments.addAll(fragment);
    }

    public void setTitles(List<String> titles) {
        mTitls.clear();
        mTitls.addAll(titles);
    }

    public void addFramgent(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTitls.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitls.get(position);
    }

    /**
     * 得到当前显示的Item
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object instanceof Fragment) {
            mCurrentFragment= (Fragment) object;
        }
    }

    public Fragment getmCurrentFragment() {
        return mCurrentFragment;
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
