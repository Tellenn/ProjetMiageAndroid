package com.example.perrink.projetmiage;


        import android.util.Log;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Horaire {

    @SerializedName("pattern")
    @Expose
    private Pattern pattern;
    @SerializedName("times")
    @Expose
    private List<Time> times = null;

    public Pattern getPattern() {
        return pattern;
    }

    public String getLigne() { return pattern.getLigne(); }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public boolean filterTimes(int time) {
        for(int i=0; i<times.size();i++){
            if(times.get(i).getRealtimeArrival() < time)
            {
                if(times.remove(i) == null){

                    Log.wtf("Error", "Tried to remove item that isn't here!");
                }
                i--;
            }
        }
        return(times.size()>0);
    }

}