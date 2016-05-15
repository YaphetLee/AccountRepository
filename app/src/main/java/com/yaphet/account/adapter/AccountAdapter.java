package com.yaphet.account.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yaphet.account.R;
import com.yaphet.account.bean.AccountBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class AccountAdapter extends BaseAdapter{

    private Context context;
    private List<AccountBean> list_account;

    public void setList(List<AccountBean> list_account){
        this.list_account = list_account;
        notifyDataSetChanged();
    }

    public AccountAdapter(Context context){
        list_account = new ArrayList<>();
        this.context = context;
    }
    @Override
    public int getCount() {
        return list_account.size();
    }

    @Override
    public Object getItem(int position) {
        return list_account.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.detail_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Log.e("infolistisisisi", list_account.size() + "");
        holder.tv_money.setText(list_account.get(position).getMoney());
        holder.tv_category.setText(list_account.get(position).getCategory());
        holder.tv_place.setText(list_account.get(position).getPlace());
        holder.tv_time.setText(list_account.get(position).getTime());
        holder.tv_payType.setText(list_account.get(position).getPayType());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_money,tv_category,tv_time,tv_place,tv_payType;

        public ViewHolder(View view){
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            tv_category = (TextView) view.findViewById(R.id.tv_cate);
            tv_payType = (TextView) view.findViewById(R.id.tv_type);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_place = (TextView) view.findViewById(R.id.tv_place);
        }
    }
}
