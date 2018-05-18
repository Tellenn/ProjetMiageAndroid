package com.example.perrink.projetmiage;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorairesArretActivity extends AppCompatActivity {


    private ProgressDialog dialog;
    private HoraireAdapter adapter;
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

        final ListView listHoraires = (ListView) findViewById(R.id.listHorraires);


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

        Call<List<Horaire>> call = api.getPassageFromStation(station.getCode());


        call.enqueue(new Callback<List<Horaire>>() {
            @Override
            public void onResponse(Call<List<Horaire>> call, Response<List<Horaire>> response) {
                //Dismiss Dialog
                dialog.dismiss();
                if (response.isSuccessful()) {

                    //Traitement des arrets
                    final List<Horaire> horaire = response.body();




                    if(ligne!=null)
                    {
                        for (int i = 0; i < horaire.size(); i++)
                        {
                            if (!horaire.get(i).getLigne().equals(ligne.getId().split(":")[1]))
                            {
                                Log.wtf("Item removed", horaire.get(i).getPattern().getId());
                                horaire.remove(i);
                                i--;
                            }
                        }
                    }

                    Log.wtf("LOG !", ""+ horaire.size());
                    // Adaptation pour affichage

                    listHoraires.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Horaire temp = horaire.get(position);
                            createNotification(""+ligne.getType()+" "+ligne.getId().split(":")[1] + " Direction " + temp.getPattern().getShortDesc(),
                                    "Prochain à "+ temp.getTimes().get(0).getRealtimeArrival()/60/60 + "H " +
                                            (temp.getTimes().get(0).getRealtimeArrival()-(temp.getTimes().get(0).getRealtimeArrival()/60/60)*60*60)/60,
                                    temp.getTimes().get(0).getRealtimeArrival());
                        }
                    });
                    if(horaire.size()==0)
                    {
                        Log.wtf("Log", "Pas de passage");
                        horaire.add(null);
                    }
                    //Log.wtf("Log", horaire.size()+" horaire.size");
                    adapter = new HoraireAdapter(HorairesArretActivity.this, horaire,ligne);
                    listView.setAdapter(adapter);


                    //Traitement de l'horaire
                    if( time != -1){
                        for (int i = 0; i < horaire.size(); i++)
                        {
                            if(!horaire.get(i).filterTimes(time))
                            {
                                horaire.remove(i);
                                i--;
                            }
                        }
                    }
                } else {
                    Log.wtf("ERROR !", "Unsuccessful request");
                    dialog.setTitle("OOPS");
                    dialog.setMessage(getString(R.string.string_getting_json_error_noresponse_message));
                    dialog.show();
                    /*try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();*/
                }
            }

            @Override
            public void onFailure(Call<List<Horaire>> call, Throwable t) {
                Log.wtf("Error !", t.getMessage().toString());
                dialog.setTitle("OOPS");
                dialog.setMessage("Nous n'arrivons pas a contacter Métromobilite");
                dialog.show();
                List<Horaire> horaire = new ArrayList<>();
                horaire.add(null);
                adapter = new HoraireAdapter(HorairesArretActivity.this, horaire,ligne);
                listView.setAdapter(adapter);
            }
        });

    }
    private final void createNotification(String title, String desc, long time){

        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);



        //long when = (time*1000 +date.getTimeInMillis() - 60000)-System.currentTimeMillis();
        long when = System.currentTimeMillis()+ 5000;
        //long when = SystemClock.elapsedRealtime() + 5000;
        Log.wtf("Log !", ""+when);
        Log.wtf("Log current time !", ""+ System.currentTimeMillis());

        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifService.class);
        Bundle b = new Bundle();
        b.putString("title",title);
        b.putString("desc",desc);
        b.putLong("when",when);
        intent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        sendBroadcast(intent);
        am.set(AlarmManager.RTC, when, pendingIntent);

    }
}
