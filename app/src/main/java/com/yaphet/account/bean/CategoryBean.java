package com.yaphet.account.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Table(name= "category")
public class CategoryBean {
    /**
     * 类别表：序号id；类别名称
     */
    @Column(name= "id",isId = true)
    private int id;
    @Column(name= "typeName")
    private String typeName;

    /*public CategoryBean(String typeName){
        this.typeName = typeName;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
