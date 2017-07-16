package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 订单
 */
public class OrderBean implements Serializable {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("cname")
    @Expose
    private String cName;

    @SerializedName("data")
    @Expose
    private String orderDataBen;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getOrderDataBen() {
        return orderDataBen;
    }

    public void setOrderDataBen(String orderDataBen) {
        this.orderDataBen = orderDataBen;
    }
}
