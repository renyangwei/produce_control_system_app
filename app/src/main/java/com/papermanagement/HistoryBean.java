package com.papermanagement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        return other;
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
                other + ", class = " + clazz + ", time" + time + ", group = " + group;
    }
}
