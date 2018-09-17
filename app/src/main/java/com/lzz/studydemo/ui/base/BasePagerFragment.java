package com.lzz.studydemo.ui.base;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lzz.studydemo.R;
import com.lzz.studydemo.ui.base.adapter.BaseFragmentPagerAdapter;

import java.util.List;

public abstract class BasePagerFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BaseFragmentPagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

    @Override
    protected void initView(View mRoot) {
        super.initView(mRoot);

        tabLayout = mRoot.findViewById(R.id.tabLayout);
        viewPager = mRoot.findViewById(R.id.viewPager);
        pagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());

        pagerAdapter.setFragments(getFragments());
        pagerAdapter.setTitles(getTitles());

        viewPager.setAdapter(pagerAdapter);

        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
            setupTabView();
        }
    }

    protected void setupTabView() {

    }
    protected abstract List<Fragment> getFragments();

    protected abstract List<String> getTitles();
}
