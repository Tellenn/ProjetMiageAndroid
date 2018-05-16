package com.example.perrink.projetmiage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
    private ProgressDialog dialog;

    private String ligne = "B"; //TODO A supprimer, doit être a nul et récupéré ensuite
    private String station = "GENPLAINEDS";// TODO A supprimer, doit être a null et recupéré ensuite
    private int time = -1; //TODO A SUPPRIMER -1 is null in our case

    private List<ArretLigne> listArretLigne;
    private ChoixDirection directions;
    private List<ChoixLigne> choixLigne;
    private ChoixLigneAdapter adapter;
    private ApiService api = RetroClient.getApiService();

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createNotification("Plop", "Ploup");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {

                if (isNetworkConnected()) {
                    /**
                     * Progress Dialog for User Interaction
                     */
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    //Creating an object of our api interface


                    /**
                     * Calling JSON
                     */
                    Call<List<ArretLigne>> call2 = api.getStopsFrom("A");
                    Log.wtf("URL Called", call2.request().url() + " ");

                    /**
                     * Enqueue Callback will be call when get response...
                     */
                    call2.enqueue(new Callback<List<ArretLigne>>() {
                        @Override
                        public void onResponse(Call<List<ArretLigne>> call, Response<List<ArretLigne>> response) {

                            if (response.isSuccessful()) {

                                listArretLigne = response.body();
                                demarrerAffichage();

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
                    });

                } else {
                    //TODO
                }
            }
        });
    }

    private void demarrerAffichage(){
        Call<List<ChoixLigne>> call = api.getPassageFromStation(station);

        call.enqueue(new Callback<List<ChoixLigne>>() {
            @Override
            public void onResponse(Call<List<ChoixLigne>> call, Response<List<ChoixLigne>> response) {
                //Dismiss Dialog
                dialog.dismiss();
                if (response.isSuccessful()) {

                    /**Traitement de la ligne*/
                    choixLigne = response.body();
                    if(ligne!=null)
                    {
                        for (int i = 0; i < choixLigne.size(); i++)
                        {
                            if (!choixLigne.get(i).getLigne().equals(ligne))
                            {
                                Log.wtf("Item removed", choixLigne.get(i).getPattern().getDesc());
                                choixLigne.remove(i);
                                i--;
                            }
                        }
                    }
                    /**Traitement de l'horraire*/
                    if( time != -1){
                        for (int i = 0; i < choixLigne.size(); i++)
                        {
                            if(!choixLigne.get(i).filterTimes(time))
                            {
                                choixLigne.remove(i);
                                i--;
                            }
                        }
                    }


                    Log.wtf("Filter done", "all time after "+time+" for line "+ligne);

                    /** Adaptation pour affichage */
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
    }
    private final void createNotification(String title, String desc){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("testNotification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent);

        mNotification.notify(1, builder.build());
    }
}
