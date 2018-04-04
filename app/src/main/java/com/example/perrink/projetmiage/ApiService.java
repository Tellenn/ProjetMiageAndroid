package com.example.perrink.projetmiage;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by perrink on 04/04/18.
 */

public interface ApiService {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("/json_data.json")
    Call<ArretList> getMyJSON();
}
