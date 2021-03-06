package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.fll.teamstorm.SQL.SafeZoneSQLAsync;
import com.fll.teamstorm.dialogs.AddressDialog;
import com.fll.teamstorm.dialogs.SafeZoneDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ListSafeZoneActivity extends ListActivity implements SafeZonesLoadedListener, SafeZoneDialogFragment.HasSQLAsync, AdapterView.OnItemLongClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    private SafeZoneSQLAsync sqlAsync;
    private List<SafeZone> safezones = new ArrayList<SafeZone>(); // Init it to an empty list

    private SafeZoneArrayAdapter adapter;

    private CheckBox showLocal, showGlobal;

    private LocationClient mLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_safe_zone);

        // Set up the SQLite helper
        this.sqlAsync = new SafeZoneSQLAsync(this, this);

        // Set up the LocationClient. This is to auto-zoom when location is available
        this.mLocationClient = new LocationClient(this, this, this);

        // Set up the adapter
        this.adapter = new SafeZoneArrayAdapter(this, R.layout.list_item_safe_zone, safezones);
        this.adapter.setLocationClient(this.mLocationClient);
        setListAdapter(this.adapter);

        // Set up checkboxes
        this.showLocal = (CheckBox) findViewById(R.id.list_show_local);
        this.showGlobal = (CheckBox) findViewById(R.id.list_show_global);

        // Create the onClick listener for the Checkboxes and assign it to them.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSafeZoneActivity.this.loadSafeZones();
            }
        };

        this.showLocal.setOnClickListener(listener);
        this.showGlobal.setOnClickListener(listener);

        ListView list = getListView();
        list.setOnItemLongClickListener(this);


    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Open the SQLite helper
        this.sqlAsync.open();
        this.loadSafeZones();

        // Connect the client.
        mLocationClient.connect();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Close the SQLite helper
        this.sqlAsync.close();

        // Close the client.
        mLocationClient.disconnect();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_safe_zone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.menu_list_new_safe_zone:
                new AddressDialog().show(getFragmentManager(), AddressDialog.TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSafeZonesLoaded(List<SafeZone> zones) {


        this.safezones.clear();
        this.safezones.addAll(zones);
        this.adapter.notifyDataSetChanged();

        Log.i(MapActivity.TAG, String.format("%d SafeZones loaded by ListSZ Activity", this.adapter.getCount()));

    }

    @Override
    public void loadSafeZones(){
        boolean local = this.showLocal.isChecked();
        boolean global = this.showGlobal.isChecked();

        // Load the SafeZones that are requested
        if (local && global) {
            this.sqlAsync.loadSafeZones();
        } else if (local) {
            this.sqlAsync.loadLocalSafeZones();
        } else if (global) {
            this.sqlAsync.loadGlobalSafeZones();
        } else {
            this.safezones.clear();
            this.adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        SafeZone sz = (SafeZone) this.getListAdapter().getItem(position);

        new SafeZoneDialogFragment(sz).show(getFragmentManager(), SafeZoneDialogFragment.TAG);

    }

    @Override
    public SafeZoneSQLAsync getSqlAsync() { return this.sqlAsync; }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

        // Vibrate for 50 milliseconds.
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        v.vibrate(50);

        SafeZone sz = (SafeZone) this.getListAdapter().getItem(position);
        new DeleteSafeZoneDialog(sz).show(getFragmentManager(), DeleteSafeZoneDialog.TAG);

        return true;
    }

    /*** GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener ***/

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class DeleteSafeZoneDialog extends DialogFragment {

        public static final String TAG = "SZ-DIALOG-DELETE";

        private SafeZoneSQLAsync sqlAsync;

        private SafeZone sz;

        public DeleteSafeZoneDialog(SafeZone sz){
            super();

            this.sz = sz;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(getString(R.string.dialog_delete_safezone_title));

            builder.setMessage(getString(R.string.dialog_delete_safezone_msg, this.sz.getTitle()));

            builder.setPositiveButton(getString(R.string.dialog_delete_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ListSafeZoneActivity.this.getSqlAsync().deleteSafeZone(sz.getId());
                }
            });

            // If cancel is selected a callback isn't needed as we just close the dialog.
            builder.setNeutralButton(R.string.string_cancel, null);

            return builder.create();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // Assuming the activity is MapActivity get the SQLHelper
            try {
                this.sqlAsync = ((SafeZoneDialogFragment.HasSQLAsync) activity).getSqlAsync();
            } catch (Exception e) {
                Log.d(TAG, "Could not get SQLAsync from calling activity.");
            }
        }

    }


}
