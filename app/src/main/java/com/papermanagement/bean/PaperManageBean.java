package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 纸箱模型
 */
public class PaperManageBean {
    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Factory")
    @Expose
    private String factory;

    @SerializedName("Other")
    @Expose
    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    @Override
    public String toString() {
        return id + "," + factory + "," + other;
    }
}
