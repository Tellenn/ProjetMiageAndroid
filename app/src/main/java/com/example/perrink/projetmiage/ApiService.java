package com.example.perrink.projetmiage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by perrink on 25/04/18.
 */

public interface ApiService {
    @GET("routers/default/index/routes/SEM:{shortName}/clusters/")
    Call<List<ArretLigne>> getStopsFrom(@Path("shortName") String shortName);

    @GET("routers/default/index/clusters/{codeStation}/stoptimes")
    Call<List<Horaire>> getPassageFromStation(@Path("codeStation") String codeStation);

    @GET("routers/default/index/routes")
    Call<List<Ligne>> getLignes();
}
