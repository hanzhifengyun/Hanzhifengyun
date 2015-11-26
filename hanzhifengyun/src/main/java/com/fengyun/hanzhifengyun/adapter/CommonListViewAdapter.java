package com.fengyun.hanzhifengyun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by hanzhifengyun on 7/23/2015.
 * 用法如下
 * mListView.setAdapter(new CommonListViewAdapter<ToolBean>(getActivity(), mListViewData, R.layout.tool_item) {
    @Override
    public void convert(CommonViewHolder viewHolder, ToolBean toolBean) {
    viewHolder.setImageResource(R.id.tool_item_image, toolBean.imageResId);
    //等价于(ImageView)viewHolder.getView(R.id.tool_item_image).setImageResource(toolBean.imageResId);
    viewHolder.setText(R.id.tool_item_text, getString(toolBean.textResId));
    }
    });
 参数1:ToolBean,指数据Bean类,此处为ToolBean;
 参数2:context,此处getActivity()获取;
 参数3:listViewData,为List<Bean>集合,此处为List<ToolBean> mListViewData;
 参数4:layoutId,为listView中item填充的布局

 只需实现一个方法convert(CommonViewHolder viewHolder, ToolBean toolBean ,int position)
 参数1:viewHolder,通过viewHolder来获取控件
         viewHolder.getView(ResId); 返回一个控件
            ResId:控件在xml文件中设置的id

 参数2:每个item的Bean实例,通过bean来获取数据设置到控件上

 参数3:当前item的position

 另:如果需要用到position,可以通过viewHolder.getPosition()获取;
 */
public abstract class CommonListViewAdapter<T> extends BaseAdapter{
    private Context mContext;
    private List<T> mData;
    private int mLayoutId;

    public CommonListViewAdapter(Context context, List<T> datas, int layoutId){
        this.mContext = context;
        this.mData = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (getCount() == 0){
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getCommonViewHolder(mContext,convertView,parent,mLayoutId);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getConvertView();
    }

    //用户需实现的方法
    public abstract void convert(CommonViewHolder viewHolder, T t, int position);
}
