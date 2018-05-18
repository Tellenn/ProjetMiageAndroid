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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * Views
     */
    private ListView listView;
    private View parentView;
    private ProgressDialog dialog;

    private String ligne = "B"; //TODO A supprimer, doit être a nul et récupéré ensuite
    private String station = "GENPLAINEDS";// TODO A supprimer, doit être a null et recupéré ensuite
    private int time = 71280; //TODO A SUPPRIMER -1 is null in our case

    private List<ArretLigne> listArretLigne;
    private List<Horraire> horraire;
    private List<Ligne> listeLigne;
    private HorraireAdapter adapter;
    private LigneAdapter ligneAdapter;
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


        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listLigne2);

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

                    Call<List<Ligne>> callLigne = api.getLignes();
                    /*Call<List<ArretLigne>> call2 = api.getStopsFrom("A");
                    Log.wtf("URL Called", call2.request().url() + " ");

                    /**
                     * Enqueue Callback will be call when get response...
                     */
                    callLigne.enqueue(new Callback<List<Ligne>>() {
                        @Override
                        public void onResponse(Call<List<Ligne>> call, Response<List<Ligne>> response) {
                            if (response.isSuccessful())
                            {
                                listeLigne = response.body();
                                Iterator<Ligne> iter = listeLigne.iterator();
                                Ligne l;
                                while(iter.hasNext())
                                {
                                    l = iter.next();

                                    if(!l.isTag())
                                    {
                                        iter.remove();
                                    }
                                }
                                dialog.dismiss();
                                afficherLigne(listeLigne,listView);
                                //TODO afficher la liste de ligne

                            } else {
                                dialog.setTitle(getString(R.string.string_getting_json_Error_noresponse));
                                dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                                dialog.show();
                                //dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Ligne>> call, Throwable t) {
                            Log.wtf("Error !", "Echec de la Callback");
                            dialog.dismiss();
                        }
                    });
                    /*call2.enqueue(new Callback<List<ArretLigne>>() {
                        @Override
                        public void onResponse(Call<List<ArretLigne>> call, Response<List<ArretLigne>> response) {

                            if (response.isSuccessful()) {
                                //On a récuperer les arrets existant de la ligne donnée en argument
                                listArretLigne = response.body();
                                // On démare la partie 2
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
                    });*/

                } else {
                    //TODO
                }
            }
        });
    }


    private void afficherLigne(List<Ligne> lignes, final ListView listView)
    {
        /** Adaptation pour affichage */

        ArrayList<String> group = new ArrayList<>();
        group.add("Tram");
        group.add("Chrono");
        group.add("Bus");

        ArrayList<Object> childs = new ArrayList<>();

        ArrayList<Ligne> tram = new ArrayList<>();
        ArrayList<Ligne> chrono = new ArrayList<>();
        ArrayList<Ligne> bus = new ArrayList<>();

        for( int i=0;i<lignes.size();i++)
        {
            Ligne ligneTemp = lignes.get(i);
            if(ligneTemp.getType().equals("Tram"))
            {
                tram.add(ligneTemp);
            } else if (ligneTemp.getType().equals("Chrono"))
            {
                chrono.add(ligneTemp);
            }else if (ligneTemp.getType().equals("Bus"))
            {
                bus.add(ligneTemp);
            }
        }

        childs.add(tram);
        childs.add(chrono);
        childs.add(bus);


        ligneAdapter = new LigneAdapter(this,lignes);
        listView.setAdapter(ligneAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent i = new Intent(getBaseContext(), ChoixArretActivity.class);
                Bundle b = new Bundle();


                Ligne l;
                l =(Ligne) adapter.getItemAtPosition(position);

                b.putSerializable("ligne", l);
                i.putExtras(b);
                startActivityForResult(i, 2);
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
