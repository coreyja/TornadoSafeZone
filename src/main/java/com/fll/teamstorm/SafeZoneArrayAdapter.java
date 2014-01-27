package com.fll.teamstorm;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;

import java.util.List;

/**
 * Created by coreyja on 1/27/14.
 */
public class SafeZoneArrayAdapter extends ArrayAdapter<SafeZone> {

    private Context context;
    private int layoutID;

    private List<SafeZone> safezones;


    public SafeZoneArrayAdapter(Context context, int layoutID, List<SafeZone> objects) {
        super(context, layoutID, objects);

        this.context = context;
        this.layoutID = layoutID;

        this.safezones = objects;
    }

    @Override
    public int getCount() {
        return this.safezones.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // If convertView is null we are creating a view to inflate it.
        // If it isn't null we are editing instead of creating a view
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(this.layoutID, parent, false);
        }

        SafeZone sz = this.getItem(position);

        ((TextView) convertView.findViewById(R.id.list_item_title)).setText(sz.getTitle());
        ((TextView) convertView.findViewById(R.id.list_item_phone)).setText(sz.getPhone());

        Log.i(MapActivity.TAG, String.format("Pos:%d Title:%s ID:%d", position, sz.getTitle(), sz.getId()));

        return convertView;
    }
}
