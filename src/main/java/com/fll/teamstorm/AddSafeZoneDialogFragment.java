package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by coreyja on 1/27/14.
 */
public class AddSafeZoneDialogFragment extends SafeZoneDialogFragment {

    public static final String TAG = "SZ-DIALOG-NEW";

    private LatLng latlng;

    public AddSafeZoneDialogFragment(LatLng latLng){
        super();

        this.latlng = latLng;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Init the texts field needed
        this.titleText = getString(R.string.dialog_add_safezone_title);
        this.positiveButtonText = getString(R.string.dialog_add_safezone_positive_text);
    }

    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = super.buildDialog();

        // If we were given a longitude and latitude when we started, auto fill these fields
        if (this.latlng != null){
            this.lat_field.setText(Double.toString(this.latlng.latitude));
            this.lng_field.setText(Double.toString(this.latlng.longitude));
        }

        return builder;

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // Create a new empty SafeZone
        SafeZone sz = new SafeZone();

        // Save the information from the fields to the new SafeZone
        this.loadSafeZoneFromDialog(sz);

        // Add the SafeZone
        this.sqlAsync.addSafeZone(sz, true);
    }
}
