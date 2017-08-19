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

    @SerializedName("Scxh")
    @Expose
    private String scxh;        //序号

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
    private String zd;        //纸度

    @SerializedName("Zbcd")
    @Expose
    private String zbcd;        //切长

    @SerializedName("Pscl")
    @Expose
    private String pscl;        //排产数量

    @SerializedName("Ddsm")
    @Expose
    private String ddms;        //说明

    @SerializedName("Zt")
    @Expose
    private String zt;

    @SerializedName("Ks")
    @Expose
    private String ks;          //剖

    @SerializedName("Sm2")
    @Expose
    private String sm2;

    @SerializedName("Zbcd2")
    @Expose
    private String zbcd2;

    @SerializedName("Xbmm")
    @Expose
    private String xbmm;

    @SerializedName("Scbh")
    @Expose
    private String scbh;

    @SerializedName("Ms")
    @Expose
    private String ms;

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

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getPscl() {
        return pscl;
    }

    public void setPscl(String pscl) {
        this.pscl = pscl;
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

    public String getScxh() {
        return scxh;
    }

    public void setScxh(String scxh) {
        this.scxh = scxh;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getSm2() {
        return sm2;
    }

    public void setSm2(String sm2) {
        this.sm2 = sm2;
    }

    public String getZbcd2() {
        return zbcd2;
    }

    public void setZbcd2(String zbcd2) {
        this.zbcd2 = zbcd2;
    }

    public String getXbmm() {
        return xbmm;
    }

    public void setXbmm(String xbmm) {
        this.xbmm = xbmm;
    }

    public String getScbh() {
        return scbh;
    }

    public void setScbh(String scbh) {
        this.scbh = scbh;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getDdms() {
        return ddms;
    }

    public void setDdms(String ddms) {
        this.ddms = ddms;
    }

    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();
//        if (!TextUtils.isEmpty(getScxh())) {
//            list.add("序号:" + getScxh());
//        }
        if (!TextUtils.isEmpty(getMxbh())) {
            list.add("订单编号:" + getMxbh());
        }
//        if (!TextUtils.isEmpty(getKhjc())) {
//            list.add("客户简称:" + getKhjc());
//        }
        if (!TextUtils.isEmpty(getZbdh())) {
            list.add("材质:" + getZbdh());
        }
        if (!TextUtils.isEmpty(getKlzhdh())) {
            list.add("楞别:" + getKlzhdh());
        }
        if (!TextUtils.isEmpty(getZd())) {
            list.add("纸度:" + getZd());
        }

        if (!TextUtils.isEmpty(getPscl())) {
            list.add("排产数量:" + getPscl());
        }
        if (!TextUtils.isEmpty(getZbcd2())) {
            list.add("切长:" + getZbcd2());
        }
        if (!TextUtils.isEmpty(getMs())) {
            list.add("米数" + getMs());
        }
        if (!TextUtils.isEmpty(getKs())) {
            list.add("剖:" + getKs());
        }
//        if (!TextUtils.isEmpty(getFinishTime())) {
//            list.add("预计完工:" + getFinishTime());
//        }
        return list;
    }
}
