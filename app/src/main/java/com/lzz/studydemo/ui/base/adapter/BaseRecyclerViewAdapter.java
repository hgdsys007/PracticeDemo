package com.lzz.studydemo.ui.base.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzz.studydemo.Bean.SerializableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by limou on 2017/6/6/9:04.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context context;
    protected List<T> datas;
    protected OnRecycleViewClick<T> itemClickListener;


    public void setOnItemClickListener(OnRecycleViewClick<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BaseRecyclerViewAdapter() {
        datas = new ArrayList<>();
    }

    public View inflate(ViewGroup parent, int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.baseonBindViewHolder(datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        } else
            return datas.size();
    }

    /**
     * 初始化数据
     *
     * @param datas
     */
    public void setAllDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * 添加数据
     *
     * @param newDatas
     */
    public void addAllDatas(List<T> newDatas) {
        this.datas.addAll(newDatas);
//        notifyItemRangeChanged(this.datas.size() - newDatas.size(), newDatas.size());
    }

    public void addData(T t) {
        datas.add(t);
    }

    public void clear() {
        datas.clear();
    }

    public void remove(int pos) {
        datas.remove(pos);
    }

    public void remove(T t) {
        datas.remove(t);
    }

    public void removeAll(ArrayList<T> data) {
        datas.retainAll(data);
    }

    public List<T> getDatas() {
        return datas;
    }

    protected static void loadPicture(ImageView view, String url) {
        //        GlideManager.getInstance().load(UIUtils.getContext(), url, view);
    }

    protected static void loadFace(ImageView view, String url) {
        //        GlideManager.getInstance().loadFace(UIUtils.getContext(), url, view);
    }

    public interface OnRecycleViewClick<T> {
        void onClick(List<T> datas, int postion);
    }

    protected void gotoActivity(Class<?> cls, Map<String, Object> map) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (null != map) {
            SerializableMap serializableMap = new SerializableMap();
            serializableMap.setMap(map);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Map", serializableMap);
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 将空指针，转换为""
     *
     * @param s
     * @return
     */
    protected String getRealText(String s) {
        return TextUtils.isEmpty(s) ? "" : s;
    }
}
