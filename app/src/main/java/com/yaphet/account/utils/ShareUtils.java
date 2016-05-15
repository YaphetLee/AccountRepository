package com.yaphet.account.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ShareUtils {

    private static SharedPreferences userShare;
    private static SharedPreferences cardShare;
    private static SharedPreferences monthShare;

    //存储用户名信息
    public static void setPassword(Context context, String password) {
        if (userShare == null)
            userShare = context.getSharedPreferences("userInfo",
                    Context.MODE_PRIVATE);
        userShare.edit().putString("userPassword", password).commit();
    }

    public static String getPassword(Context context) {
        if (userShare == null)
            userShare = context.getSharedPreferences("userInfo",
                    Context.MODE_PRIVATE);
        return userShare.getString("userPassword", "");
    }

    //存储信用卡额度信息
    public static void setCardMoney(Context context,String cardMoney){
        if(cardShare==null)
            cardShare = context.getSharedPreferences("cardInfo",Context.MODE_PRIVATE);
            cardShare.edit().putString("cardMoney",cardMoney).commit();
    }

    public static String getCardMoney(Context context){
        if(cardShare==null)
            cardShare = context.getSharedPreferences("cardInfo",Context.MODE_PRIVATE);
        return cardShare.getString("cardMoney","0");
    }


    //存储月支出额度信息
    public static void setMonthMoney(Context context,String monthMoney){
        if(monthShare==null)
            monthShare = context.getSharedPreferences("monthInfo",Context.MODE_PRIVATE);
        monthShare.edit().putString("monthMoney",monthMoney).commit();
    }

    public static String getMonthMoney(Context context){
        if(monthShare==null)
            monthShare = context.getSharedPreferences("monthInfo",Context.MODE_PRIVATE);
        return monthShare.getString("monthMoney","0");
    }
}
