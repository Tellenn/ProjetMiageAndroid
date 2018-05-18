package com.example.perrink.projetmiage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Pault on 18/05/2018.
 */

public class OnLigneClick implements  ExpandableListView.OnChildClickListener  {

    Context context;
    LigneAdapterExpand adapter;

    public OnLigneClick(Context context,LigneAdapterExpand adapter)
    {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {

        Intent i = new Intent(context, ChoixArretActivity.class);
        Bundle b = new Bundle();


        Ligne l;
        l = (Ligne) adapter.getChild(groupPosition,childPosition);

        b.putSerializable("ligne", l);
        i.putExtras(b);
        new ChoixArretActivity().startActivityForResult(i,2);

        return true;
    }
}
