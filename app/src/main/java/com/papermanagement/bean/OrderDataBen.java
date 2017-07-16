package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 订单信息
 */
public class OrderDataBen implements Serializable {

    @SerializedName("mxbh")
    @Expose
    private String mxbh;        //订单号

    @SerializedName("khjc")
    @Expose
    private String khjc;        //客户简称

    @SerializedName("zbdh")
    @Expose
    private String zbdh;        //材质

    @SerializedName("klzhdh")
    @Expose
    private String klzhdh;      //楞别

    @SerializedName("xdzd")
    @Expose
    private String xdzd;        //纸度

    @SerializedName("ddsl-tlsl")
    @Expose
    private String ddsl;        //排产数量

    @SerializedName("zbcd")
    @Expose
    private String zbcd;        //切长

    @SerializedName("ks")
    @Expose
    private String ks;

    @SerializedName("finish_time")
    @Expose
    private String finishTime;

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

    public String getKlzhdh() {
        return klzhdh;
    }

    public void setKlzhdh(String klzhdh) {
        this.klzhdh = klzhdh;
    }

    public String getXdzd() {
        return xdzd;
    }

    public void setXdzd(String xdzd) {
        this.xdzd = xdzd;
    }

    public String getDdsl() {
        return ddsl;
    }

    public void setDdsl(String ddsl) {
        this.ddsl = ddsl;
    }

    public String getZbcd() {
        return zbcd;
    }

    public void setZbcd(String zbcd) {
        this.zbcd = zbcd;
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
