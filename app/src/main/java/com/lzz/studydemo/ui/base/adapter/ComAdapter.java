package com.lzz.studydemo.ui.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lzz.studydemo.ui.base.ViewHolder;

import java.util.List;


/**
 * TODO:
 * 十年生死两茫茫，写程序，到天亮。
 * 千行代码，Bug何处藏。
 * 纵使上线又怎样，朝令改，夕断肠。
 * 领导每天新想法，天天改，日日忙。
 * 相顾无言，惟有泪千行。
 * 每晚灯火阑珊处，夜难寐，又加班。
 */

public abstract class ComAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> list;
    private int itemLyoutId;

    public ComAdapter(Context context, List<T> list, int itemLyoutId) {
        this.itemLyoutId = itemLyoutId;
        this.context = context;
        this.list = list;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void NotifyDataChanged(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }


    public abstract void convert(ViewHolder vh, T item, int position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(context, convertView, parent, itemLyoutId, position);
    }

}
