package com.papermanagement.bean;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.papermanagement.Utils.DataUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单信息
 */
public class OrderDataBen implements Serializable {

    @SerializedName("Mxbh")
    @Expose
    private String mxbh;        //订单号

    @SerializedName("Khjc")
    @Expose
    private String khjc;        //客户简称

    @SerializedName("Zbdh")
    @Expose
    private String zbdh;        //材质

    @SerializedName("Klzhdh")
    @Expose
    private String klzhdh;      //楞别

    @SerializedName("Xdzd")
    @Expose
    private String xdzd;        //纸度

    @SerializedName("Pscl")
    @Expose
    private String ddsl;        //排产数量

    @SerializedName("Zbcd")
    @Expose
    private String zbcd;        //切长

    @SerializedName("Ks")
    @Expose
    private String ks;          //剖

    @SerializedName("FinishTime")
    @Expose
    private String finishTime;      //预计完工时间

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
        return DataUtils.parasTime(finishTime);
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
        if (!TextUtils.isEmpty(getKlzhdh())) {
            list.add("楞别:" + getKlzhdh());
        }
        if (!TextUtils.isEmpty(getXdzd())) {
            list.add("纸度:" + getXdzd());
        }
        if (!TextUtils.isEmpty(getDdsl())) {
            list.add("排产数量:" + getDdsl());
        }
        if (!TextUtils.isEmpty(getZbcd())) {
            list.add("切长:" + getZbcd());
        }
        if (!TextUtils.isEmpty(getKs())) {
            list.add("剖:" + getKs());
        }
        if (!TextUtils.isEmpty(getFinishTime())) {
            list.add("预计完工:" + getFinishTime());
        }
        return list;
    }
}
