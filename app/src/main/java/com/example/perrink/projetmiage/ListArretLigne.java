package com.example.perrink.projetmiage;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by perrink on 25/04/18.
 */

public class ListArretLigne {

    @SerializedName("ArretLignes")
    @Expose
    private ArrayList<ArretLigne> ArretLignes = new ArrayList<>();

    /**
     * @return The contacts
     */
    public ArrayList<ArretLigne> getArrets() {
        return ArretLignes;
    }

    /**
     * @param contacts The contacts
     */
    public void setArrets(ArrayList<ArretLigne> contacts) {
        this.ArretLignes = contacts;
    }
}
