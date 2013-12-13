package com.fll.teamstorm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appspot.perfect_atrium_421.safezones.Safezones;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.appspot.perfect_atrium_421.safezones.model.SafeZoneCollection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity implements GoogleMap.InfoWindowAdapter{

    public static final String TAG = "FLL-TS";

    private GoogleMap mMap;

    private Safezones mService;

    private List<SafeZone> safeZones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Setup service for Endpoints
        Safezones.Builder builder = new Safezones.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        mService = builder.build();

        // Set up the Map Fragment
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(this);

        // Retrieve SafeZones from Endpoints
        this.populateSafeZones();

    }

    private void populateSafeZones() {
        // Create the async task to get the SafeZones from endpoints
        // The async task will refresh the markers when it completes

        new QueryForSafeZones().execute();

    }

    private void refreshMarkers() {
        mMap.clear();

        for (SafeZone s : safeZones){
            if (s.getLocation() != null){
                mMap.addMarker(s.generateMarkerOptions());
            }
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
        if (sz.hasMaxOccupancy()){
            ((TextView)v.findViewById(R.id.info_capacity)).setText(getString(R.string.format_capacity, sz.getMaxOccupancy()));
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
            ((TextView)v.findViewById(R.id.info_extrainfo)).setText(sz.getExtraInfo());
        } else {
            v.findViewById(R.id.info_extrainfo).setVisibility(View.GONE);
        }

        return v;
    }

    class QueryForSafeZones extends AsyncTask<Void, Void, SafeZoneCollection> {

        @Override
        protected SafeZoneCollection doInBackground(Void... voids) {
            SafeZoneCollection safezones = null;
            try {
                safezones = mService.safezone().list().setLimit(50L).execute();
            } catch (IOException e){
                Log.d(TAG, "Failed Loading " + e);
            }

            return safezones;
        }

        protected void onPostExecute(SafeZoneCollection result){
            super.onPostExecute(result);

            if (result == null){
                Log.d(TAG, "Failed Loading, result is null");
                return;
            }

            List<com.appspot.perfect_atrium_421.safezones.model.SafeZone> list = result.getItems();

            if (list == null || list.isEmpty()){
                Log.d(TAG, "Failed Failed. Result received, but no SafeZones found.");
                return;
            }

            // Save the list retrieved
            MapActivity.this.safeZones = list;

            // Update the markers on the map
            refreshMarkers();
        }
    }
}
