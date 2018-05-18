package com.example.perrink.projetmiage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
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
    private List<Horaire> horaire;
    private List<Ligne> listeLigne;
    private HoraireAdapter adapter;
    private LigneAdapter ligneAdapter;
    private LigneAdapterExpand ligneAdapterExpand;
    private ApiService api = RetroClient.getApiService();

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.choix_ligne_expand);
        /**
         * Array List for Binding Data from JSON to this List
         */
        listArretLigne = new ArrayList<>();


        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listLigne2);



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
        } else {
            //TODO
        }
    }


    private void afficherLigne(List<Ligne> lignes, final ListView listView)
    {
        /** Adaptation pour affichage */

        ArrayList<String> group = new ArrayList<>();
        group.add("Tram");
        group.add("Chrono");
        group.add("Flexo");

        ArrayList<ArrayList<Ligne>> childs = new ArrayList<>();

        ArrayList<Ligne> tram = new ArrayList<>();
        ArrayList<Ligne> chrono = new ArrayList<>();
        ArrayList<Ligne> bus = new ArrayList<>();

        for( int i=0;i<lignes.size();i++)
        {
            Ligne ligneTemp = lignes.get(i);
            if(ligneTemp.getType().equals("TRAM"))
            {
                tram.add(ligneTemp);
            } else if (ligneTemp.getType().equals("CHRONO"))
            {
                chrono.add(ligneTemp);
            }else if (ligneTemp.getType().equals("FLEXO"))
            {
                bus.add(ligneTemp);
            }
        }



        childs.add(tram);
        childs.add(chrono);
        childs.add(bus);


        ExpandableListView expandbleLis = (ExpandableListView) findViewById(R.id.expand);
        final LigneAdapterExpand mNewAdapter = new LigneAdapterExpand(this,group, childs);
        mNewAdapter.setInflater(
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                this);
        expandbleLis.setAdapter(mNewAdapter);
    }
}
