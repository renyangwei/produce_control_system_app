package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 完工资料
 */

public class FinishTimeBean {

    @SerializedName("mxbh")
    @Expose
    private String mxbh;        //订单号

    @SerializedName("khjc")
    @Expose
    private String khjc;        //客户简称

    @SerializedName("zbdh")
    @Expose
    private String zbdh;        //材质

    @SerializedName("zbkd")
    @Expose
    private String zbkd;        //纸板宽

    @SerializedName("hgpsl")
    @Expose
    private String hgpsl;       //合格数

    @SerializedName("blpsl")
    @Expose
    private String blpsl;       //不良数

    @SerializedName("pcsl")
    @Expose
    private String pcsl;        //排产数

    @SerializedName("zbcd")
    @Expose
    private String zbcd;        //切长

    @SerializedName("start_time ")
    @Expose
    private String startTime;   //开始时间

    @SerializedName("finish_time")
    @Expose
    private String finishTime;   //完工时间

    public String getMxbh() {
        return mxbh;
    }

    public void setMxbh(String mxbh) {
        this.mxbh = mxbh;
    }

    public String getKhjc() {
        return khjc;
    }

    public void setKhjc(String khjc) {
        this.khjc = khjc;
    }

    public String getZbdh() {
        return zbdh;
    }

    public void setZbdh(String zbdh) {
        this.zbdh = zbdh;
    }

    public String getZbkd() {
        return zbkd;
    }

    public void setZbkd(String zbkd) {
        this.zbkd = zbkd;
    }

    public String getHgpsl() {
        return hgpsl;
    }

    public void setHgpsl(String hgpsl) {
        this.hgpsl = hgpsl;
    }

    public String getBlpsl() {
        return blpsl;
    }

    public void setBlpsl(String blpsl) {
        this.blpsl = blpsl;
    }

    public String getPcsl() {
        return pcsl;
    }

    public void setPcsl(String pcsl) {
        this.pcsl = pcsl;
    }

    public String getZbcd() {
        return zbcd;
    }

    public void setZbcd(String zbcd) {
        this.zbcd = zbcd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
