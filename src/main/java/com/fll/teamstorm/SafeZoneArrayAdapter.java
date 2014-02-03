package com.fll.teamstorm;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by coreyja on 1/27/14.
 */
public class SafeZoneArrayAdapter extends ArrayAdapter<SafeZone> {

    private Context context;
    private int layoutID;

    private List<SafeZone> safezones;

    private LocationClient locationClient;


    public SafeZoneArrayAdapter(Context context, int layoutID, List<SafeZone> objects) {
        super(context, layoutID, objects);

        this.context = context;
        this.layoutID = layoutID;

        this.safezones = objects;
    }

    public void setLocationClient(LocationClient lc) {
        this.locationClient = lc;
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
        ((TextView) convertView.findViewById(R.id.list_item_phone)).setText(Utils.formatPhoneNumber(sz.getPhone()));

        ImageView img = (ImageView) convertView.findViewById(R.id.list_item_pin);

        img.setImageResource(sz.getMapPinDrawableID());

        // Calculate and display the current distance from this SafeZone
        Location loc = this.locationClient.getLastLocation();
        LatLng dest = new LatLng(sz.getLocation().getLat(), sz.getLocation().getLon());

        double miles = Utils.kmToMiles(Utils.distanceBetweenPoints(loc, dest));
        ((TextView)convertView.findViewById(R.id.list_item_dist)).setText(context.getString(R.string.info_format_dist_away, miles));

        return convertView;
    }
}
