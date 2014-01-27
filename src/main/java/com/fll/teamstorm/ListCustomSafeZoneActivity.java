package com.fll.teamstorm;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListView;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;

import java.util.ArrayList;
import java.util.List;

public class ListCustomSafeZoneActivity extends ListActivity implements OnSafeZonesLoadedListener, SafeZoneDialogFragment.HasSQLAsync{

    private SafeZoneSQLAsync sqlAsync;
    private List<SafeZone> safezones = new ArrayList<SafeZone>(); // Init it to an empty list

    private SafeZoneArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_custom_safe_zone);

        // Set up the SQLite helper
        this.sqlAsync = new SafeZoneSQLAsync(this, this);

        // Set up the adapter
        this.adapter = new SafeZoneArrayAdapter(this, R.layout.list_item_safe_zone, safezones);
        setListAdapter(this.adapter);
    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Open the SQLite helper
        this.sqlAsync.open();
        this.sqlAsync.loadCustomSafeZones();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Close the SQLite helper
        this.sqlAsync.close();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_custom_safe_zone, menu);
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
    public void onSafeZonesLoaded(List<SafeZone> zones) {


        this.safezones.clear();
        this.safezones.addAll(zones);
        this.adapter.notifyDataSetChanged();

        Log.i(MapActivity.TAG,String.format("%d SafeZones loaded by ListCustomSZ Activity", this.adapter.getCount()));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        SafeZone sz = (SafeZone) this.getListAdapter().getItem(position);

        new EditSafeZoneDialogFragment(sz).show(getFragmentManager(), AddSafeZoneDialogFragment.TAG);

        Log.i(MapActivity.TAG, String.format("Title:%s", sz.getTitle()));
    }

    @Override
    public SafeZoneSQLAsync getSqlAsync() { return this.sqlAsync; }
}
