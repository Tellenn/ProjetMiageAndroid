package com.example.perrink.projetmiage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by perrink on 04/04/18.
 */

public class ArretMetroAdapter {
    List<ArretList> ArretList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ArretMetroAdapter(Context context, List<ArretList> objects) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.ArretList = objects;
    }

    public ArretList getItem(int position) {
        return ArretList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_arret, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ArretList item = getItem(position);

        vh.textViewId.setText(item.getPattern().getId());
        vh.textViewName.setText(item.getPattern().getDesc());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewName;
        public final TextView textViewId;

        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewId = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewId = (TextView) rootView.findViewById(R.id.textViewId);
            return new ViewHolder(rootView, textViewName, textViewId);
        }
    }
}
