package com.example.perrink.projetmiage;

/**
 * Created by perrink on 16/05/18.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LigneAdapter extends ArrayAdapter<Ligne> {

    List<Ligne> lignes;
    Context context;
    public LayoutInflater mInflater;
    public List<String> groupItem;
    public List<Ligne> tempChild;
    public List<Object> Childtem = new ArrayList<Object>();

    /*
    public LigneAdapter (Context context,List<String> grList, List<Object> childItem)
    {
        this.context=context;
        groupItem = grList;
        this.Childtem = childItem;
    }

    /*
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<Ligne>) Childtem.get(groupPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.childrow, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView2);
        text.setText(tempChild.get(childPosition).getId());
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, tempChild.get(childPosition),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    */
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
        //Log.wtf("Log", name);
        vh.textViewName.setText(name);
        return vh.rootView;
    }

    /*
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) Childtem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.Childtem.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.Childtem.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_main, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textView1);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    /*@Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }*/

    /*
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }*/

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
