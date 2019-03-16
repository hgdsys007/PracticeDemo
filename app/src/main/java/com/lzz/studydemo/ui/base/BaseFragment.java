package com.lzz.studydemo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzz.studydemo.Logger;

public abstract class BaseFragment extends Fragment {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
    }

    protected void initBundle(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            Logger.e("BaseFragmentOnCreateView,mRoot存在", "是否有parent" + parent);
            if (parent != null)
                parent.removeView(mRoot);

        } else {
            Logger.e("BaseFragmentOnCreateView,mRoot不存在");
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
//            ButterKnife.bind(this, mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initView(mRoot);
            initData();
        }
        return mRoot;
    }

    protected void initData() {

    }

    protected void initView(View mRoot) {

    }

    protected void onBindViewBefore(View mRoot) {

    }

    protected void onRestartInstance(Bundle bundle) {

    }

    protected abstract int getLayoutId();
}
