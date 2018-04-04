package com.example.perrink.projetmiage;

/**
 * Created by perrink on 04/04/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArretList {

    @SerializedName("pattern")
    @Expose
    private ArretPattern pattern;
    @SerializedName("times")
    @Expose
    private List<ArretTime> times = null;

    public ArretPattern getPattern() {
        return pattern;
    }

    public void setPattern(ArretPattern pattern) {
        this.pattern = pattern;
    }

    public List<ArretTime> getTimes() {
        return times;
    }

    public void setTimes(List<ArretTime> times) {
        this.times = times;
    }

}