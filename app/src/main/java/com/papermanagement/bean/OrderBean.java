package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.papermanagement.Utils.DataUtils;

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

    @SerializedName("StartTime ")
    @Expose
    private String startTime;   //开始时间

    @SerializedName("FinishTime")
    @Expose
    private String finishTime;   //完工时间

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

    public String getStartTime() {
        return DataUtils.parasTime(startTime);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return DataUtils.parasTime(finishTime);
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String toString() {
        return "id:" + getId() + ",cname:" + getcName() + ",data" + getOrderDataBen();
    }

}
