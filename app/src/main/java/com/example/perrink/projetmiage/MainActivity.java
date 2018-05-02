package com.example.perrink.projetmiage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    /**
     * Views
     */
    private ListView listView;
    private View parentView;
    public TextView debugMoi;

    private List<ArretLigne> listArretLigne;
    private ChoixDirection directions;
    private List<ChoixLigne> choixLigne;
    //private ArretLigneAdapter adapter;
    //private HorraireArretAdapter adapter;
    private ChoixLigneAdapter adapter;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Array List for Binding Data from JSON to this List
         */
        listArretLigne = new ArrayList<>();

        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {

                if (isNetworkConnected()) {
                    final ProgressDialog dialog;
                    /**
                     * Progress Dialog for User Interaction
                     */
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    //Creating an object of our api interface
                    ApiService api = RetroClient.getApiService();

                    /**
                     * Calling JSON
                     */
                    //Call<List<ArretLigne>> call = api.getStopsFrom("A");
                    //Call<ChoixDirection> call = api.getTimeFrom();
                    Call<List<ChoixLigne>> call = api.getPassageFromStation("GENPLAINEDS");
                    Log.wtf("URL Called", call.request().url() + " ");

                    /**
                     * Enqueue Callback will be call when get response...
                     */
                    /*call.enqueue(new Callback<List<ArretLigne>>() {
                        @Override
                        public void onResponse(Call<List<ArretLigne>> call, Response<List<ArretLigne>> response) {
                            //Dismiss Dialog
                            dialog.dismiss();
                            if (response.isSuccessful()) {

                                listArretLigne = response.body();
                                adapter = new ArretLigneAdapter(MainActivity.this, listArretLigne);
                                listView.setAdapter(adapter);

                            } else {
                                dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                                dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                                dialog.show();
                                //dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ArretLigne>> call, Throwable t) {
                            Log.wtf("Error !", "Echec de la Callback");
                            dialog.dismiss();
                        }
                    });*/
                    /*
                    call.enqueue(new Callback<ChoixDirection>() {
                        @Override
                        public void onResponse(Call<ChoixDirection> call, Response<ChoixDirection> response) {
                            //Dismiss Dialog
                            dialog.dismiss();
                            if (response.isSuccessful()) {

                                directions = response.body();
                                adapter = new HorraireArretAdapter(MainActivity.this, directions.get0().getArrets());
                                listView.setAdapter(adapter);

                            } else {
                                dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                                dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                                dialog.show();
                                //dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChoixDirection> call, Throwable t) {
                            Log.wtf("Error !", t.getMessage().toString());
                            dialog.dismiss();
                        }
                    });
*/
                    call.enqueue(new Callback<List<ChoixLigne>>() {
                        @Override
                        public void onResponse(Call<List<ChoixLigne>> call, Response<List<ChoixLigne>> response) {
                            //Dismiss Dialog
                            dialog.dismiss();
                            if (response.isSuccessful()) {

                                choixLigne = response.body();
                                adapter = new ChoixLigneAdapter(MainActivity.this, choixLigne);
                                listView.setAdapter(adapter);

                            } else {
                                dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                                dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                                dialog.show();
                                //dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ChoixLigne>> call, Throwable t) {
                            Log.wtf("Error !", t.getMessage().toString());
                            dialog.dismiss();
                        }
                    });
                } else {
                    //TODO
                }
            }
        });
    }
}
