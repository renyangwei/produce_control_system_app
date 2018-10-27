package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.papermanagement.Utils.DataUtils;

/**
 * 历史数据
 */
public class HistoryBean {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("Factory")
    @Expose
    private String factory;

    @SerializedName("Other")
    @Expose
    private String other;

    @SerializedName("Class")
    @Expose
    private String clazz;

    @SerializedName("Time")
    @Expose
    private String time;

    @SerializedName("Group")
    @Expose
    private String group;

    public int getId() {
        return id;
    }

    public String getFactory() {
        return factory;
    }

    public String getOther() {
        return pareseOther(other);
    }

    public String getClazz() {
        return clazz;
    }

    public String getTime() {
        return time.split(" ")[0];
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "id = " + id + ",factory = " + factory + ", other = " +
                other + ", class = " + clazz + ", time=" + time + ", group = " + group;
    }

    private String pareseOther(String other) {
        String[] others = other.split("&");
        for (String oth : others) {
            if (oth.contains("开工时间") || oth.contains("完成时间")) {
                String[] s = oth.split("=");
                other = other.replace(s[1], DataUtils.parasTime(s[1]));
            }
        }
        return other;
    }

}
