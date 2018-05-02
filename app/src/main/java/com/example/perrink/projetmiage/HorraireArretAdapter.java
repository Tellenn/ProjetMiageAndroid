package com.example.perrink.projetmiage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by perrink on 25/04/18.
 */

public class HorraireArretAdapter extends ArrayAdapter<HorraireArret> {

    List<HorraireArret> HorraireArrets;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public HorraireArretAdapter(Context context, List<HorraireArret> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        HorraireArrets = objects;
    }

    @Override
    public HorraireArret getItem(int position) {
        return HorraireArrets.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_horraire_arret, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        HorraireArret item = getItem(position);

        vh.textViewName.setText(item.getStopName());
        /*Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long howMany = 86400 - (c.getTimeInMillis()/1000-System.currentTimeMillis()/1000);
        long timeleft= (howMany - (item.getTrips().get(0))+7200)/60/60 ;*/
        Integer temps = item.getTrips().get(0)+7200;
        Integer heures = temps/60/60;
        Integer minutes = (temps - heures*60*60)/60;
            vh.textViewNext.setText("Prochain à "+heures+"h"+minutes);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewNext;
        public final TextView textViewName;

        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewNext) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewNext = textViewNext;
        }

        public static HorraireArretAdapter.ViewHolder create(RelativeLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewNext = (TextView) rootView.findViewById(R.id.textViewNext);
            return new HorraireArretAdapter.ViewHolder(rootView, textViewName, textViewNext);
        }
    }
}
