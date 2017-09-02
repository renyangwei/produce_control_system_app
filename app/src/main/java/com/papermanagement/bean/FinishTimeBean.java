package com.papermanagement.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.papermanagement.Utils.DataUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  完工信息
 */
public class FinishTimeBean implements Serializable{

    @SerializedName("Mxbh")
    private String Mxbh;

    @SerializedName("Khjc")
    private String Khjc;

    @SerializedName("Ms")
    private String Ms;

    @SerializedName("Bzbh")
    private String Bzbh;

    @SerializedName("Zbmd")
    private String Zbmd;

    @SerializedName("Klzhdh")
    private String Klzhdh;

    @SerializedName("Zd")
    private String Zd;

    @SerializedName("Pcsl")
    private String Pcsl;

    @SerializedName("Hgpsl")
    private String Hgpsl;

    @SerializedName("Blpsl")
    private String Blpsl;

    @SerializedName("Xbmm")
    private String Xbmm;

    @SerializedName("Zbcd2")
    private String Zbcd2;

    @SerializedName("Ks")
    private String Ks;

    @SerializedName("Js")
    private String Js;

    @SerializedName("StopTime")
    private String StopTime;

    @SerializedName("StopSpec")
    private String StopSpec;

    @SerializedName("Ys")
    private String Ys;

    @SerializedName("Shl")
    private String Shl;

    private String StartTime;

    private String FinishTime;


    public void setMxbh(String Mxbh) {
        this.Mxbh = Mxbh;
    }
    public String getMxbh() {
        return Mxbh;
    }

    public void setKhjc(String Khjc) {
        this.Khjc = Khjc;
    }
    public String getKhjc() {
        return Khjc;
    }

    public void setMs(String Ms) {
        this.Ms = Ms;
    }
    public String getMs() {
        return Ms;
    }

    public void setBzbh(String Bzbh) {
        this.Bzbh = Bzbh;
    }
    public String getBzbh() {
        return Bzbh;
    }

    public void setZbmd(String Zbmd) {
        this.Zbmd = Zbmd;
    }
    public String getZbmd() {
        return Zbmd;
    }

    public void setKlzhdh(String Klzhdh) {
        this.Klzhdh = Klzhdh;
    }
    public String getKlzhdh() {
        return Klzhdh;
    }

    public void setZd(String Zd) {
        this.Zd = Zd;
    }
    public String getZd() {
        return Zd;
    }

    public void setPcsl(String Pcsl) {
        this.Pcsl = Pcsl;
    }
    public String getPcsl() {
        return Pcsl;
    }

    public void setHgpsl(String Hgpsl) {
        this.Hgpsl = Hgpsl;
    }
    public String getHgpsl() {
        return Hgpsl;
    }

    public void setBlpsl(String Blpsl) {
        this.Blpsl = Blpsl;
    }
    public String getBlpsl() {
        return Blpsl;
    }

    public void setXbmm(String Xbmm) {
        this.Xbmm = Xbmm;
    }
    public String getXbmm() {
        return Xbmm;
    }

    public void setZbcd2(String Zbcd2) {
        this.Zbcd2 = Zbcd2;
    }
    public String getZbcd2() {
        return Zbcd2;
    }

    public void setKs(String Ks) {
        this.Ks = Ks;
    }
    public String getKs() {
        return Ks;
    }

    public void setJs(String Js) {
        this.Js = Js;
    }
    public String getJs() {
        return Js;
    }

    public void setStopTime(String StopTime) {
        this.StopTime = StopTime;
    }
    public String getStopTime() {
        return StopTime;
    }

    public void setStopSpec(String StopSpec) {
        this.StopSpec = StopSpec;
    }
    public String getStopSpec() {
        return StopSpec;
    }

    public void setYs(String Ys) {
        this.Ys = Ys;
    }
    public String getYs() {
        return Ys;
    }

    public void setShl(String Shl) {
        this.Shl = Shl;
    }
    public String getShl() {
        return Shl;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        FinishTime = finishTime;
    }

    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("班组:" + DataUtils.isEmpty(getBzbh()));
        list.add("客户:" + DataUtils.isEmpty(getKhjc()));
        list.add("材质:" + DataUtils.isEmpty(getZbmd()));
        list.add("楞别:" + DataUtils.isEmpty(getKlzhdh()));
        list.add("门幅:" + DataUtils.isEmpty(getZd()));
        list.add("排产:" + DataUtils.isEmpty(getPcsl()));
        list.add("合格:" + DataUtils.isEmpty(getHgpsl()));
        list.add("不良:" + DataUtils.isEmpty(getBlpsl()));
        list.add("修边:" + DataUtils.isEmpty(getXbmm()));
        list.add("切长:" + DataUtils.isEmpty(getZbcd2()));
        list.add("板宽:" + DataUtils.isEmpty(getKs()));
        list.add("机速:" + DataUtils.isEmpty(getJs()));
        list.add("停时:" + DataUtils.isEmpty(getStopTime()));
        list.add("停次:" + DataUtils.isEmpty(getStopSpec()));
        list.add("用时:" + DataUtils.isEmpty(getYs()));
        list.add("损耗:" + DataUtils.isEmpty(getShl()));
        return list;
    }
}