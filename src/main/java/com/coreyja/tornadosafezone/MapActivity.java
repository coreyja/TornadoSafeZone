package com.coreyja.tornadosafezone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MapActivity extends Activity implements GoogleMap.InfoWindowAdapter{

    private GoogleMap mMap;

    private ArrayList<SafeZone> safeZones = new ArrayList<SafeZone>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mMap.setMyLocationEnabled(true);

        mMap.setInfoWindowAdapter(this);

        this.populateSafeZones();

        this.refreshMarkers();

    }

    private void populateSafeZones() {
        // In the future this may be removed when we add async for endpoints
        // For now just create some dummy SafeZones to add.

        safeZones.clear();

        safeZones.add(new SafeZone(new LatLng(39.483333, -87.324444), "Logan Library", "(303) 555-1234", 8, 0, 20, 0, 200, "Go to the 'Learning Center' in case of Tornado"));

        safeZones.add(new SafeZone(new LatLng(39.48261667, -87.3295), "White Chapel"));

    }

    private void refreshMarkers() {
        mMap.clear();

        for (SafeZone s : safeZones){
            mMap.addMarker(s.generateMarkerOptions());
        }
    }

    private SafeZone getSafeZoneFromTitle(String t){

        for (SafeZone s : safeZones){
            if (s.getTitle().equals(t)){
                return s;
            }
        }

        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.info_window, null);
        SafeZone sz = this.getSafeZoneFromTitle(marker.getTitle());

        // If you don't find a SafeZone to match this marker, just return null which won't display any info window
        if (sz == null) {
            return null;
        }

        // Set the title of the info window
        ((TextView)v.findViewById(R.id.info_title)).setText(sz.getTitle());

        // Set the hours if they exists. And hide the TextView if it doesn't
        if (sz.hasHours()){
            ((TextView)v.findViewById(R.id.info_hours)).setText(sz.getFormattedHours());
        } else {
            v.findViewById(R.id.info_hours).setVisibility(View.GONE);
        }

        // Set the capacity if it exists. And hide the TextView if it doesn't
        if (sz.hasCapacity()){
            ((TextView)v.findViewById(R.id.info_capacity)).setText(getString(R.string.format_capacity, sz.getCapacity()));
        } else {
            v.findViewById(R.id.info_capacity).setVisibility(View.GONE);
        }

        // Do the same as above for the phone number
        if (sz.hasPhone()){
            ((TextView)v.findViewById(R.id.info_phone)).setText(sz.getPhone());
        } else {
            v.findViewById(R.id.info_phone).setVisibility(View.GONE);
        }

        // And last but not least, extra info
        if (sz.hasExtraInfo()){
            ((TextView)v.findViewById(R.id.info_extrainfo)).setText(sz.getExtra_info());
        } else {
            v.findViewById(R.id.info_extrainfo).setVisibility(View.GONE);
        }

        return v;
    }
}
