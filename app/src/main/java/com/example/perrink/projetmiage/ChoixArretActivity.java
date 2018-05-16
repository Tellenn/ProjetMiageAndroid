package com.example.perrink.projetmiage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class ChoixArretActivity extends AppCompatActivity {

    private List<ArretLigne> listArretLigne;
    private List<ChoixLigne> choixLigne;
    private List<Ligne> listeLigne;
    private ChoixLigneAdapter adapter;
    private ApiService api = RetroClient.getApiService();

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_arret);
    }
}
