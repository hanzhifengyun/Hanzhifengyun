package com.fengyun.hanzhifengyun.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hanzhifengyun on 7/23/2015.
 */
public class CommonViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId){
        this.mViews = new SparseArray<View>();
        this.mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    //通过此方法来获取CommonViewHolder
    public static CommonViewHolder getCommonViewHolder(Context context,View convertView, ViewGroup parent, int layoutId){
        if (convertView == null){
            return new CommonViewHolder(context, parent, layoutId);
        }else {
            CommonViewHolder viewHolder = (CommonViewHolder) convertView.getTag();
            return viewHolder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId 控件的id
     * @param <T> View的子类
     * @return
     */
    public <T extends View>T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }


    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 提供TextView快速设置文本的方法
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 提供ImageView快速设置图片的方法
     * @param viewId
     * @param resId Resource
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int resId){
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }
    /**
     * 提供ImageView快速设置图片的方法
     * @param viewId
     * @param bitmap  bitmap
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

  
}
