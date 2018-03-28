package com.example.perrink.projetmiage;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by perrink on 28/03/18.
 */

public class MyService extends IntentService {

    public MyService(){
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.metromobilite.fr")
                .build();

        MetroService metroserv = retrofit.create(MetroService.class);
        metroserv.getAnswer().enqueue(new Callback<MetroMobilite>() {

            @Override
            public void onResponse(Call<MetroMobilite> call, Response<MetroMobilite> response) {
                MetroMobilite metromob = response.body();


            }

            @Override
            public void onFailure(Call<MetroMobilite> call, Throwable throwable) {
                Log.e("MyService","Failure",throwable);
            }
        });
    }
}
