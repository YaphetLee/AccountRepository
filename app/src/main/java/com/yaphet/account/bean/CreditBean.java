package com.yaphet.account.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/5/7.
 */
@Table( name ="credit")
public class CreditBean {
    /**
     *  id   消费金额
     */
    @Column(name = "id" ,isId= true)
    private int id;
    @Column(name = "money")
    private String money;

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
}
