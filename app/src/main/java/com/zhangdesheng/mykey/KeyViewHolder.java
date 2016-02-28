package com.zhangdesheng.mykey;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2016/2/28.
 */
public class KeyViewHolder {
    private SparseArray<View> views;
    private View myConvertView;          //就是子项的布局

    KeyViewHolder(int layoutId, Context context){
        views = new SparseArray<>();
        myConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        myConvertView.setTag(this);
    }

    public static KeyViewHolder getViewHolder(int layoutId, Context context, View convertView){
        if (convertView == null){
            return new KeyViewHolder(layoutId,context);
        }
        return (KeyViewHolder)convertView.getTag();
    }

    public <T extends View>  T getView(int viewId){
        View view =views.get(viewId);
        if (view == null){
            view = myConvertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getMyConvertView(){
        return myConvertView;
    }
}

