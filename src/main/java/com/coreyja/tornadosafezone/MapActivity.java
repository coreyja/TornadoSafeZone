package com.coreyja.tornadosafezone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends Activity {

    private GoogleMap mMap;

    private ArrayList<SafeZone> safeZones = new ArrayList<SafeZone>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mMap.setMyLocationEnabled(true);

        this.populateSafeZones();

        this.refreshMarkers();

    }

    private void populateSafeZones() {
        // In the future this may be removed when we add async for endpoints
        // For now just create some dummy SafeZones to add.

        safeZones.clear();
        safeZones.add(new SafeZone(new LatLng(39.483333, -87.324444), "Logan Library"));

    }

    private void refreshMarkers() {
        mMap.clear();

        for (SafeZone s : safeZones){
            mMap.addMarker(s.generateMarkerOptions());
        }
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


}
