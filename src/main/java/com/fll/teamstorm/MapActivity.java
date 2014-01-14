package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appspot.perfect_atrium_421.safezones.Safezones;
import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.appspot.perfect_atrium_421.safezones.model.SafeZoneCollection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.List;

public class MapActivity extends Activity implements GoogleMap.InfoWindowAdapter, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = "FLL-TS";

    private GoogleMap mMap;

    private Safezones mService;

    private List<SafeZone> safeZones;

    private LocationClient mLocationClient;

    private  boolean hasZoomedIntoInitialLocation;

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
        mMap.setOnInfoWindowClickListener(this);

        // Set up the LocationClient. This is to auto-zoom when location is available
        this.mLocationClient = new LocationClient(this, this, this);
        // If we have a savedInstanceState then not an initial load, so don't auto-zoom.
        if (savedInstanceState == null) {
            this.hasZoomedIntoInitialLocation = false;
        } else {
            this.hasZoomedIntoInitialLocation = true;
        }

        // Retrieve SafeZones from Endpoints
        this.populateSafeZones();

    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }


    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
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
       // TODO: Add a menu to the map Activity to do cool stuff eventually

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /********** GoogleMap.InfoWindowAdapter **********/

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

        // Calculate and display the current distance from this SafeZone
        Location loc = this.mLocationClient.getLastLocation();
        LatLng dest = marker.getPosition();
        double miles = Utils.kmToMiles(Utils.distanceBetweenPoints(loc, dest));
        ((TextView)v.findViewById(R.id.info_dist_away)).setText(getString(R.string.info_format_dist_away, miles));


        // Set the hours if they exists. And hide the TextView if it doesn't
        if (sz.hasHours()){
            ((TextView)v.findViewById(R.id.info_hours)).setText(sz.getTodaysFormattedHours());
        } else {
            v.findViewById(R.id.info_hours).setVisibility(View.GONE);
        }

        // Set the capacity if it exists. And hide the TextView if it doesn't
        if (sz.hasOccupancy()){
            ((TextView)v.findViewById(R.id.info_occupancy)).setText(getString(R.string.format_occupancy, sz.getOccupancy()));
        } else {
            v.findViewById(R.id.info_occupancy).setVisibility(View.GONE);
        }

        // Set the capacity if it exists. And hide the TextView if it doesn't
        if (sz.hasMaxOccupancy()){
            ((TextView)v.findViewById(R.id.info_max_occupancy)).setText(getString(R.string.format_max_occupancy, sz.getMaxOccupancy()));
        } else {
            v.findViewById(R.id.info_max_occupancy).setVisibility(View.GONE);
        }

        // Sets the occupancy info text field if this SafeZone is nearing capacity.
        double percentFull = sz.getPercentFull();
        if (sz.hasOccupancy() && sz.hasMaxOccupancy() && percentFull > 0.75){

            if (percentFull > 0.9) {
                ((TextView)v.findViewById(R.id.info_occupancy_info)).setText(getString(R.string.occupancy_90));
                ((TextView)v.findViewById(R.id.info_occupancy_info)).setTextColor(getResources().getColor(R.color.occupancy_90));
            } else { // percentFull > 0.75
                ((TextView)v.findViewById(R.id.info_occupancy_info)).setText(getString(R.string.occupancy_75));
                ((TextView)v.findViewById(R.id.info_occupancy_info)).setTextColor(getResources().getColor(R.color.occupancy_75));
            }

        } else {
            v.findViewById(R.id.info_occupancy_info).setVisibility(View.GONE);
        }

        // Do the same as above for the phone number
        if (sz.hasPhone()){
            // Use Utils to format the Phone Number pretty if it matches the format we are expecting
            ((TextView)v.findViewById(R.id.info_phone)).setText(Utils.formatPhoneNumber(sz.getPhone()));
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

    /********** GooglePlayServicesClient.ConnectionCallbacks **********/

    @Override
    public void onConnected(Bundle bundle) {

        // If we haven't already zoomed, we must have just been created. Zoom in now
        if (!this.hasZoomedIntoInitialLocation){
            // Get the location
            Location loc = this.mLocationClient.getLastLocation();

            // Create the CameraPosition object with the current location and default zoom
            CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(loc.getLatitude(), loc.getLongitude())).zoom(getResources().getInteger(R.integer.default_zoom_level)).build();
            this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));

            // Set this flag so everytime we resume this Activity it doesn't try and change the zoom
            this.hasZoomedIntoInitialLocation = true;
        }
    }

    @Override
    public void onDisconnected() {

    }

    /********** GooglePlayServicesClient.OnConnectionFailedListener **********/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /********** GoogleMap.OnInfoWindowClickListener **********/

    @Override
    public void onInfoWindowClick(Marker marker) {
        SafeZone sz = this.getSafeZoneFromTitle(marker.getTitle());

        new IntentLauncherDialogFragment(sz).show(getFragmentManager(), IntentLauncherDialogFragment.TAG);

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
                Log.d(TAG, "Failed Loading, result is null. Load from the SQLite database.");

                SafeZoneSQLHelper helper = new SafeZoneSQLHelper(MapActivity.this);
                helper.open();

                MapActivity.this.safeZones = helper.getAllSafeZones();

                refreshMarkers();

                return;
            }

            List<com.appspot.perfect_atrium_421.safezones.model.SafeZone> list = result.getItems();

            if (list == null || list.isEmpty()){
                Log.d(TAG, "Failed Failed. Result received, but no SafeZones found.");
                return;
            }

            SafeZoneSQLHelper helper = new SafeZoneSQLHelper(MapActivity.this);
            helper.open();

            for (SafeZone sz : list){
                helper.addSafeZone(sz);
            }

            // Save the list retrieved
            MapActivity.this.safeZones = list;

            // Update the markers on the map
            refreshMarkers();
        }
    }

    public class IntentLauncherDialogFragment extends DialogFragment {

        public static final String TAG = "SZ-INTENTS";

        private SafeZone sz;

        public IntentLauncherDialogFragment(SafeZone sz) {
            this.sz = sz;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(sz.getTitle());

            builder.setPositiveButton(R.string.intents_directions_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /* Launch gMaps with directions from current location to SafeZone */
                    Location src = mLocationClient.getLastLocation();
                    GeoPtMessage dest = sz.getLocation();

                    String uri = String.format("http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", src.getLatitude(), src.getLongitude(), dest.getLat(), dest.getLon());
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            });

            // If this SafeZone has a phone give the option to call it.
            if (sz.hasPhone()) {
                builder.setMessage(R.string.intents_message_both);

                builder.setNeutralButton(R.string.intents_call_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    /* Launch Dialer with number pre-filled */
                        String uri = "tel:" + sz.getPhone();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }
                });
            } else {
                builder.setMessage(R.string.intents_message_no_phone);
            } 

            return builder.create();
        }
    }
}
