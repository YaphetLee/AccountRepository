package com.yaphet.account.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.yaphet.account.R;
import com.yaphet.account.adapter.AccountAdapter;
import com.yaphet.account.application.MyApplication;
import com.yaphet.account.bean.AccountBean;
import com.yaphet.account.bean.CategoryBean;
import com.yaphet.account.view.TitleBuilder;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ActivityAnaylse extends BaseActivity implements View.OnClickListener {

    private EditText et_month;
    private Button bt_search;
    private FrameLayout frame_container;
    private DbManager dbManager;
    private List<AccountBean> list_account;
    private List<CategoryBean> list_category;
    private List<AccountBean> list_accout_detail;

    private List<AccountBean> list_money;   //现金
    private List<AccountBean> list_credit;  //信用卡

    private PieChart pieChart,pieChart1;
    private PieData pieData,pieData1;


    @Override
    public int setLayout() {
        return R.layout.activity_expend_anaylse;
    }

    @Override
    public void findView() {
        new TitleBuilder(mContext).setMiddleTitleText("支出分析").setLeftImageRes(R.mipmap.layout_top_back);
        et_month = (EditText) findViewById(R.id.et_month);
        bt_search = (Button) findViewById(R.id.bt_search);
        // frame_container = (FrameLayout) findViewById(R.id.frame_container);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart1 = (PieChart) findViewById(R.id.pieChart1);
        //pieChart.setForegroundGravity(Gravity.CENTER);
        //frame_container.addView(pieChart, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setListener() {
        bt_search.setOnClickListener(this);
        new TitleBuilder(mContext).setLeftTextOrImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAnaylse.this.finish();
            }
        });
    }

    @Override
    public void loadData() {
        list_category = new ArrayList<>();
        list_account = new ArrayList<>();
        list_accout_detail = new ArrayList<>();
        list_money = new ArrayList<>();
        list_credit = new ArrayList<>();
        //初始化数据库管理器
        MyApplication applicationContext = (MyApplication) getApplicationContext();
        dbManager = x.getDb(applicationContext.getDaoConfig());
        try {
            list_category = dbManager.selector(CategoryBean.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_search) {
            String month = et_month.getText().toString();
            if (TextUtils.isEmpty(month)) {
                showToast("请输入月份");
                return;
            }
            if (list_accout_detail.size() > 0) {  //当重新计算消费时，清空上次输入月份的记录
                list_accout_detail.clear();
            }
            //清空上月记录
            if(list_money.size()>0){
                list_money.clear();
            }
            if(list_credit.size()>0){
                list_credit.clear();
            }
            sortData(month);
            pieData = gengerData(list_money);
            pieData1 = gengerData(list_credit);
            if (pieData != null) {
                showChart(pieChart, pieData);
            } else {
                if(pieData1!=null){
                    showChart(pieChart1,pieData1);
                }else{
                    return;
                }
                return;
            }


        }
    }

    //支付方式区分
    private void sortData(String month){
        try {
            list_account = dbManager.selector(AccountBean.class).findAll();
            for (int i = 0; i < list_account.size(); i++) {
                AccountBean accountBean = list_account.get(i);
                if (month.equals(accountBean.getTime().substring(5, 7))) {
                    Log.e("info", "执行到了这里");
                    if(!accountBean.getCategory().equals("收入")) {
                        list_accout_detail.add(accountBean);   //得到当月的账户表
                    }
                } else {
                    continue;
                }
            }
            for(int i = 0;i<list_accout_detail.size();i++){
                AccountBean account  = list_accout_detail.get(i);
                Log.e("info", account.toString()+account.getMoney()+account.getPayType());
                if("现金支付".equals(account.getPayType())){
                    list_money.add(account);
                }else if(account.getPayType().equals("信用卡支付")){
                    list_credit.add(account);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //显示现金支出分析
    private PieData gengerData(List<AccountBean> list_accout_detail) {
            if (list_accout_detail.size() > 0) {
                //如果搜索当前的月份是有账户列表的话3
                double total_money = 0;
                for (int i = 0; i < list_accout_detail.size(); i++) {
                    //计算当月所消费的总金额
                    total_money = total_money + Double.parseDouble(list_accout_detail.get(i).getMoney());
                }
                Log.e("info", total_money + "");
                ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容

//            for (int i = 0; i < list_category.size(); i++) {
//                xValues.add(list_category.get(i).getTypeName());  //饼块上显示成类别
//            }

                ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
                String category = "";
                for (int i = 0; i < list_category.size(); i++) {
                    double money = 0;
                    for (int j = 0; j < list_accout_detail.size(); j++) {
                        if (list_accout_detail.get(j).getCategory().equals(list_category.get(i).getTypeName())) {
                            money = money + Double.parseDouble(list_accout_detail.get(j).getMoney());
                            category = list_category.get(i).getTypeName();
                        }
                    }
                    if (money > 0) {
                        float quer = (float) ((money / total_money) * 100);
                        Log.e("inffff", quer + "==");
                        yValues.add(new Entry(quer, i));
                        xValues.add(category);
                    }
                }
                //y轴的集合
                PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
                pieDataSet.setSliceSpace(1.0f); //设置个饼状图之间的距离
                pieDataSet.setSelectionShift(2.0f);
                pieDataSet.setValueTextSize(15f);
                pieDataSet.setValueTextColor(Color.RED);
                ArrayList<Integer> colors = new ArrayList<Integer>();

                for (int i = 0; i < xValues.size(); i++) {
                    // 饼图颜色
                    colors.add(Color.rgb(85 + i * 20, 80 + i * 5, 140 + i * 4));
                }

                pieDataSet.setColors(colors);

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                float px = 5 * (metrics.densityDpi / 160f);
                pieDataSet.setSelectionShift(px); // 选中态多出的长度
                PieData pieData = new PieData(xValues, pieDataSet);
                return pieData;
            } else {
                showToast("当月没有消费记录!");
                return null;
            }
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(80f);  //半径
        pieChart.setTransparentCircleRadius(24f); // 半透明圈
        pieChart.setHoleRadius(45f);  //实心圆
        // pieChart.setHoleColor(Color.YELLOW);
        // pieChart.setBackgroundColor(Color.WHITE);
        if(pieData == this.pieData){
            pieChart.setDescription("本月支出分析");
        }else {
            pieChart.setDescription("本月信用卡支出分析");
        }

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("本月支出详情");  //饼状图中间的文字
        pieChart.setCenterTextColor(Color.RED);
        pieChart.setCenterTextSizePixels(30f);
        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }
}
