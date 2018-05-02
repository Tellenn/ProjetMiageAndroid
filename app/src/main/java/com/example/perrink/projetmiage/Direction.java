package com.example.perrink.projetmiage;

/**
 * Created by perrink on 25/04/18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Direction {
    @SerializedName("arrets")
    @Expose
    private List<HorraireArret> arrets = null;
    @SerializedName("prevTime")
    @Expose
    private Long prevTime;
    @SerializedName("nextTime")
    @Expose
    private Long nextTime;

    public List<HorraireArret> getArrets() {
        return arrets;
    }

    public void setArrets(List<HorraireArret> arrets) {
        this.arrets = arrets;
    }

    public Long getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Long prevTime) {
        this.prevTime = prevTime;
    }

    public Long getNextTime() {
        return nextTime;
    }

    public void setNextTime(Long nextTime) {
        this.nextTime = nextTime;
    }
}
