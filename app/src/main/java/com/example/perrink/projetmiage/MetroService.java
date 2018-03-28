package com.example.perrink.projetmiage;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by perrink on 28/03/18.
 */

public interface MetroService {
    @GET("api")
    Call<MetroMobilite> getAnswer();
}
