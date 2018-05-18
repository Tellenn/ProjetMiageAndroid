package com.example.perrink.projetmiage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoixArretActivity extends AppCompatActivity {

    private List<ArretLigne> listArretLigne;
    private ArretLigneAdapter adapter;
    private ProgressDialog dialog;
    private ApiService api = RetroClient.getApiService();




    private String station = null;// TODO A supprimer, doit être a null et recupéré ensuite
    private int time = -1; //TODO A SUPPRIMER -1 is null in our case

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Ligne l = (Ligne) getIntent().getExtras().getSerializable("ligne");
        String ligne = l.getShortName();
        Log.wtf("Log !", ligne);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_arret);

        listArretLigne = new ArrayList<>();

        final ListView listView = (ListView) findViewById(R.id.listeArret);

        dialog = new ProgressDialog(ChoixArretActivity.this);
        dialog.setTitle(getString(R.string.string_getting_json_title));
        dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.show();

        Call<List<ArretLigne>> call2 = api.getStopsFrom(ligne);
        call2.enqueue(new Callback<List<ArretLigne>>() {
            @Override
            public void onResponse(Call<List<ArretLigne>> call, Response<List<ArretLigne>> response) {

                if (response.isSuccessful()) {
                    //On a récuperer les arrets existant de la ligne donnée en argument
                    listArretLigne = response.body();
                    // On démare la partie 2
                    dialog.dismiss();
                    afficheArret(listView,listArretLigne,l);

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ArretLigne>> call, Throwable t) {
                Log.wtf("Error !", "Echec de la Callback");

            }
        });
    }

    private void afficheArret(final ListView listView, List<ArretLigne> listArretLigne, final Ligne ligne){


        /*



                    Log.wtf("Filter done", "all time after "+time+" for line "+ligne);

                    // Adaptation pour affichage
                */
                    adapter = new ArretLigneAdapter(ChoixArretActivity.this, listArretLigne);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent i = new Intent(getBaseContext(), HorairesArretActivity.class);
                            Bundle b = new Bundle();


                            ArretLigne l;
                            l =(ArretLigne) adapter.getItemAtPosition(position);

                            b.putSerializable("arret", l);
                            i.putExtras(b);

                            b.putSerializable("ligne", ligne);
                            i.putExtras(b);

                            startActivityForResult(i, 2);
                        }
                    });

               /* } else {
                    dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                    dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                    dialog.show();
                    //dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Horraire>> call, Throwable t) {
                Log.wtf("Error !", t.getMessage().toString());
                dialog.dismiss();
            }
        });
        */
    }
}
