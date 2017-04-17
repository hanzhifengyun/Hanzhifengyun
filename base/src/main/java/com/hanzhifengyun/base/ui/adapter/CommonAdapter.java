package com.hanzhifengyun.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by hanzhifengyun on 7/23/2015.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private OnItemClickListener<T> mOnItemClickListener;


    public CommonAdapter setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getCommonViewHolder(mContext, convertView, parent, mLayoutId, position);
        convert(viewHolder, getItem(position));
        convertView = viewHolder.getConvertView();
        if (mOnItemClickListener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(getItem(position));
                }
            });
        }
        return convertView;
    }


    public abstract void convert(CommonViewHolder viewHolder, T t);


    public interface OnItemClickListener<T> {
        void onItemClick(T t);
    }


}
