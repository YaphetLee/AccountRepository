package com.yaphet.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yaphet.account.R;
import com.yaphet.account.bean.CategoryBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CategorySortAdapter extends BaseAdapter{

    private List<CategoryBean> list_category;
    private Context context;
    private DbManager dbManager;

    public CategorySortAdapter(List<CategoryBean> list_category, Context context, DbManager dbManager){
        this.list_category = list_category;
        this.context = context;
        this.dbManager = dbManager;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.category_sort_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_category.setText(list_category.get(position).getTypeName());
//        holder.btn_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                list_category.add(0, list_category.get(position));
//                list_category.remove(list_category.get(position-1));
//                notifyDataSetChanged();
//                Toast.makeText(context,"置顶成功",Toast.LENGTH_SHORT).show();
////                try {
////                    dbManager.delete(list_category.get(position));
////                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
////                     list_category.remove(list_category.get(position));
////                     notifyDataSetChanged();
////                } catch (DbException e) {
////                    e.printStackTrace();
////                }
//            }
//        });
        return convertView;
    }

    class ViewHolder{
        private TextView tv_category;
       // private Button btn_category;
        public  ViewHolder(View view){
            tv_category = (TextView) view.findViewById(R.id.tv_category);
          //  btn_category = (Button) view.findViewById(R.id.bt_sort_category);
        }
    }
}
