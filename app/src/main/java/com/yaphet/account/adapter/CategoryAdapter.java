package com.yaphet.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yaphet.account.R;
import com.yaphet.account.bean.CategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CategoryAdapter extends BaseAdapter{

    private List<CategoryBean> list_category;
    private Context context;

    public CategoryAdapter(List<CategoryBean> list_category,Context context){
        this.list_category = list_category;
        this.context = context;
    }

    public void setList_category(List<CategoryBean> list_category){
        this.list_category  = list_category;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list_category.size();
    }

    @Override
    public Object getItem(int position) {
        return list_category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_category.setText(list_category.get(position).getTypeName());
        return convertView;
    }

    class ViewHolder{
        private TextView tv_category;

        public  ViewHolder(View view){
            tv_category = (TextView) view.findViewById(R.id.tv_category);
        }
    }
}
