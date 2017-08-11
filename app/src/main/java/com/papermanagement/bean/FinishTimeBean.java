package com.papermanagement.bean;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.papermanagement.Utils.DataUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 完工资料
 */

public class FinishTimeBean implements Serializable {

    @SerializedName("Mxbh")
    @Expose
    private String mxbh;        //订单号

    @SerializedName("Khjc")
    @Expose
    private String khjc;        //客户简称

    @SerializedName("Zbdh")
    @Expose
    private String zbdh;        //材质

    @SerializedName("Zbkd")
    @Expose
    private String zbkd;        //纸板宽

    @SerializedName("Hgpsl")
    @Expose
    private String hgpsl;       //合格数

    @SerializedName("Blpsl")
    @Expose
    private String blpsl;       //不良数

    @SerializedName("Pcsl")
    @Expose
    private String pcsl;        //排产数

    @SerializedName("Zbcd")
    @Expose
    private String zbcd;        //切长

    private String startTime;   //开始时间

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

    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(getMxbh())) {
            list.add("订单号:" + getMxbh());
        }
        if (!TextUtils.isEmpty(getKhjc())) {
            list.add("客户简称:" + getKhjc());
        }
        if (!TextUtils.isEmpty(getZbdh())) {
            list.add("材质:" + getZbdh());
        }
        if (!TextUtils.isEmpty(getZbkd())) {
            list.add("纸板宽:" + getZbkd());
        }
        if (!TextUtils.isEmpty(getHgpsl())) {
            list.add("合格数:" + getHgpsl());
        }
        if (!TextUtils.isEmpty(getBlpsl())) {
            list.add("不良数:" + getBlpsl());
        }
        if (!TextUtils.isEmpty(getPcsl())) {
            list.add("排产数:" + getPcsl());
        }
        if (!TextUtils.isEmpty(getZbcd())) {
            list.add("切长:" + getZbcd());
        }
        if (!TextUtils.isEmpty(getStartTime())) {
            list.add("开始时间:" + getStartTime());
        }
        if (!TextUtils.isEmpty(getFinishTime())) {
            list.add("完工时间:" + getFinishTime());
        }
        return list;
    }
}
