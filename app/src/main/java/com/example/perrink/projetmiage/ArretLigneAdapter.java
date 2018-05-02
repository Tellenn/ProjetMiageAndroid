package com.example.perrink.projetmiage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by perrink on 25/04/18.
 */

public class ArretLigneAdapter extends ArrayAdapter<ArretLigne> {

    List<ArretLigne> ArretLigne;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ArretLigneAdapter(Context context, List<ArretLigne> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        ArretLigne = objects;
    }

    @Override
    public ArretLigne getItem(int position) {
        return ArretLigne.get(position);
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

        ArretLigne item = getItem(position);

        vh.textViewCode.setText(item.getCode());
        vh.textViewName.setText(item.getName());
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

