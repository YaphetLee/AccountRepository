package com.yaphet.account.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yaphet.account.R;
import com.yaphet.account.bean.CategoryBean;

import org.w3c.dom.Text;
import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CategoryRenameAdapter extends BaseAdapter{

    private List<CategoryBean> list_category;
    private Context context;
    private DbManager dbManager;
    private AlertDialog mDialog;

    public CategoryRenameAdapter(List<CategoryBean> list_category, Context context, DbManager dbManager){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.category_rename_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_category.setText(list_category.get(position).getTypeName());
        holder.btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                showDialog(v);
            }
        });
        return convertView;
    }

    class ViewHolder{
        private TextView tv_category;
        private Button btn_category;
        public  ViewHolder(View view){
            tv_category = (TextView) view.findViewById(R.id.tv_category);
            btn_category = (Button) view.findViewById(R.id.bt_rename_category);
        }
    }

    //显示alertDialog
    private void showDialog(final View view){
        //自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        mDialog = builder.create();
        View payView = LayoutInflater.from(context).inflate(R.layout.rename_category_settings,null);
        final Button bt_ok = (Button) payView.findViewById(R.id.bt_ok);
        final Button bt_cancel = (Button)payView.findViewById(R.id.bt_cancel);
        final EditText et_typeName = (EditText) payView.findViewById(R.id.et_typename);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeName = et_typeName.getText().toString();
                if(TextUtils.isEmpty(typeName)){
                    Toast.makeText(context, "名字不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                int position = (int) view.getTag();
                Log.e("=====>>", position+"");
                list_category.get(position).setTypeName(typeName);
                notifyDataSetChanged();
                try {
                    //   dbManager.delete(list_category.get(position));UPDATE Person SET FirstName = 'Fred' WHERE LastName = 'Wilson'
                    //String sql = "update category set typeName ='"+typeName+"'where id ='"+(position + 1) + "'";
                    CategoryBean categoryBean = list_category.get(position);
                    categoryBean.setTypeName(typeName);
                    dbManager.saveOrUpdate(categoryBean);
//                    String sql  = "update category set typeName ='nihao' where id = '3'";
//                    dbManager.execNonQuery(new SqlInfo(sql));
//                    dbManager.execNonQuery(sql);
                    Toast.makeText(context,"重命名成功",Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.setView(payView);
        mDialog.show();
    }
}
