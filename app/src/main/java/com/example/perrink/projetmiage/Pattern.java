package com.example.perrink.projetmiage;

/**
 * Created by perrink on 02/05/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pattern {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("dir")
    @Expose
    private Integer dir;
    @SerializedName("shortDesc")
    @Expose
    private String shortDesc;

    public String getId() {
        return id;
    }

    public String getLigne() { return id.split(":")[1];}

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDir() {
        return dir;
    }

    public void setDir(Integer dir) {
        this.dir = dir;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

}