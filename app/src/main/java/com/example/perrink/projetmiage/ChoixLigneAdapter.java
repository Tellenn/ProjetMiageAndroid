package com.example.perrink.projetmiage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by perrink on 02/05/18.
 */

public class ChoixLigneAdapter extends ArrayAdapter<ChoixLigne> {

    List<ChoixLigne> ArretLignes;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ChoixLigneAdapter(Context context, List<ChoixLigne> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        ArretLignes = objects;
    }

    @Override
    public ChoixLigne getItem(int position) {
        return ArretLignes.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_arret, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ChoixLigne item = getItem(position);
        if(item == null)
        {
            Log.wtf("Error", "item in ChoixLigneAdapter is null!");
        }
        Integer heure = item.getTimes().get(0).getRealtimeArrival()/60/60;
        Integer minute = (item.getTimes().get(0).getRealtimeArrival()-heure*60*60)/60;
        if(minute<10)
        {
            vh.textViewCode.setText("Prochain à "+heure%24+"h0"+minute);
        }else
        {
            vh.textViewCode.setText("Prochain à "+heure%24+"h"+minute);
        }
        String tram = "Tram " + item.getPattern().getId().split(":")[1] + " direction " + item.getPattern().getShortDesc();
        vh.textViewName.setText(tram);
        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewCode;
        public final TextView textViewName;

        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewCode) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewCode = textViewCode;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewCode = (TextView) rootView.findViewById(R.id.textViewCode);
            return new ViewHolder(rootView, textViewCode, textViewName);
        }
    }
}