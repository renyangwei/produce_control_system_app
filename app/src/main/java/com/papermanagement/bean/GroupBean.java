package com.papermanagement.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class GroupBean {

    @SerializedName("Group")
    @Expose
    private String group;

    public String getGroup() {
        return group;
    }
}
