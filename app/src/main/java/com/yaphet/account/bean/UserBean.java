package com.yaphet.account.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Table( name = "user")
public class UserBean {
    @Column(name = "id" , isId = true)
    private int id;
    @Column(name = "userName")
    private String userName;
    @Column( name ="userPassword")
    private String userPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
