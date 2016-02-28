package com.zhangdesheng.mykey;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public abstract class Adapter<T> extends BaseAdapter {
        private List<T> datas;
        private Context myContext;
        private int mylayoutId;

        Adapter(List<T> datas, Context myContext, int mylayoutId){
            this.datas = datas;
            this.myContext = myContext;
            this.mylayoutId = mylayoutId;
        }

        @Override
        public int getCount(){
            return datas.size();
        }

        public T getItem(int position){  //得到实例
            return datas.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        public abstract void convert(KeyViewHolder viewHolder, T position);
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            KeyViewHolder keyviewHolder = KeyViewHolder.getViewHolder(mylayoutId, myContext, convertView);
            convert(keyviewHolder, getItem(position)/*传入的是viewholder和当前操作的对象*/);
            return keyviewHolder.getMyConvertView();
        }
}


