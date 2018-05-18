package com.example.perrink.projetmiage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorairesArretActivity extends AppCompatActivity {


    private ProgressDialog dialog;
    private HorraireAdapter adapter;
    private ApiService api = RetroClient.getApiService();
    private int time=-1;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires_arret);

        final Ligne ligne = (Ligne) getIntent().getExtras().getSerializable("ligne");

        ArretLigne station = (ArretLigne) getIntent().getExtras().getSerializable("arret");
        String s = station.getCode();
        Log.wtf("Log !", ligne.getId().split(":")[1]);
        Log.wtf("Log !", s);

        final ListView listView = (ListView) findViewById(R.id.listHorraires);

        dialog = new ProgressDialog(HorairesArretActivity.this);
        dialog.setTitle(getString(R.string.string_getting_json_title));
        dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.show();

        Call<List<Horraire>> call = api.getPassageFromStation(station.getCode());


        call.enqueue(new Callback<List<Horraire>>() {
            @Override
            public void onResponse(Call<List<Horraire>> call, Response<List<Horraire>> response) {
                //Dismiss Dialog
                dialog.dismiss();
                if (response.isSuccessful()) {

                    //Traitement des arrets
                    List<Horraire> horraire = response.body();




                    if(ligne!=null)
                    {
                        for (int i = 0; i < horraire.size(); i++)
                        {
                            if (!horraire.get(i).getLigne().equals(ligne.getId().split(":")[1]))
                            {
                                Log.wtf("Item removed", horraire.get(i).getPattern().getId());
                                horraire.remove(i);
                                i--;
                            }
                        }
                    }

                    Log.wtf("LOG !", ""+ horraire.size());
                    // Adaptation pour affichage

                    adapter = new HorraireAdapter(HorairesArretActivity.this, horraire);
                    listView.setAdapter(adapter);


                    //Traitement de l'horraire
                    if( time != -1){
                        for (int i = 0; i < horraire.size(); i++)
                        {
                            if(!horraire.get(i).filterTimes(time))
                            {
                                horraire.remove(i);
                                i--;
                            }
                        }
                    }
                } else {
                    Log.wtf("ERROR !", "Unsuccessful request");
                    dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                    dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                    dialog.show();
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Horraire>> call, Throwable t) {
                Log.wtf("Error !", t.getMessage().toString());
                dialog.dismiss();
            }
        });

    }
}
