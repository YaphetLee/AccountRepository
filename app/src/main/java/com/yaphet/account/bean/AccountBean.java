package com.yaphet.account.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Table(name = "account")
public class AccountBean {

    /**
     * 账目表：序号id；金额；类别；时间；地点；支付方式
     */
    @Column( name= "id",isId = true)
    private int id;
    @Column(name = "money")
    private String money;
    @Column(name = "time")
    private String time;
    @Column(name = "category")
    private String category;
    @Column(name = "place")
    private String place;
    @Column(name = "payType")
    private String payType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
