package com.lzz.studydemo.ui.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by limou on 2017/6/6/9:09.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 更新视图
     *
     * @param t       数据
     * @param postion 在RecyclerView的位置
     */
    public abstract void onBindViewHolder(T t, int postion);

    /**
     * @param t       数据
     * @param postion 在RecyclerView的位置
     */
    public void baseonBindViewHolder(T t, int postion) {
        onBindViewHolder(t, postion);
    }
}
