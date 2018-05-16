package com.example.perrink.projetmiage;

/**
 * Created by perrink on 16/05/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class LigneAdapter extends ArrayAdapter<Ligne>{

    List<Ligne> lignes;
    Context context;
    private LayoutInflater mInflater;

    public LigneAdapter(Context context, List<Ligne> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        lignes = objects;
    }

    @Override
    public Ligne getItem(int position) {
        return lignes.get(position);
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


        Ligne item = getItem(position);
        if(item == null)
        {
            Log.wtf("Error", "item in LigneAdapter is null!");
        }


        String name = "Type :"+ item.getMode() +"\nLigne :" + item.getId().split(":")[1] + "\nNom " + item.getLongName();
        Log.wtf("Error", name);
        vh.textViewName.setText(name);
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
