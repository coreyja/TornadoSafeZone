package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

/**
 * Created by coreyja on 1/19/14.
 */
public class AddSafeZoneDialogFragment extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {

    public static final String TAG = "SZ-ADDNEW";

    private LatLng latlng;

    private EditText title_field, lat_field, lng_field, curr_cap_field, max_cap_field, phone_field, extra_field;
    private TextView hours_label;
    private LinearLayout hours_cont;

    private SafeZoneSQLHelper SQLhelper;

    public AddSafeZoneDialogFragment() {
        super();
    }

    public AddSafeZoneDialogFragment(LatLng latlng){
        this();

        this.latlng = latlng;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.dialog_add_safezone_title));

        // Inflate the layout and add it to the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_safezone, null);
        builder.setView(v);

        // Get all the fields and save them here
        title_field = (EditText) v.findViewById(R.id.dialog_add_safezone_title);
        lat_field = (EditText) v.findViewById(R.id.dialog_add_safezone_latitude);
        lng_field = (EditText) v.findViewById(R.id.dialog_add_safezone_longitude);
        curr_cap_field = (EditText) v.findViewById(R.id.dialog_add_safezone_current_capacity);
        max_cap_field = (EditText) v.findViewById(R.id.dialog_add_safezone_max_capacity);
        phone_field = (EditText) v.findViewById(R.id.dialog_add_safezone_phone);
        extra_field = (EditText) v.findViewById(R.id.dialog_add_safezone_extra);

        hours_label = ((TextView) v.findViewById(R.id.dialog_add_safezone_hours));

        hours_cont = (LinearLayout) v.findViewById(R.id.dialog_add_safezone_hours_container);

        ((TextView) v.findViewById(R.id.dialog_add_safezone_hours)).setOnClickListener(this);

        builder.setPositiveButton(R.string.dialog_add_safezone_positive_text , this);

        // The callback can be null, cause all we need to do it close the dialog.
        builder.setNeutralButton(R.string.string_cancel, null);

        // If we were given a longitude and latitude when we started, auto fill these fields
        if (this.latlng != null){
            this.lat_field.setText(Double.toString(this.latlng.latitude));
            this.lng_field.setText(Double.toString(this.latlng.longitude));
        }

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Assuming the activity is MapActivity get the SQLHelper
        try {
            this.SQLhelper = ((MapActivity) activity).getSQLHelper();
        } catch (Exception e) {
            Log.d(TAG, "Could not get SQLHelper from calling activity.");
        }
    }

    /********** onClick Listener for the Dialog Buttons **********/
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // Get all the info, and create a SafeZone
        SafeZone sz = new SafeZone();

        sz.setTitle(title_field.getText().toString());
        sz.setOccupancy(Long.valueOf(curr_cap_field.getText().toString()));
        sz.setMaxOccupancy(Long.valueOf(max_cap_field.getText().toString()));
        sz.setPhone(phone_field.getText().toString());
        sz.setExtraInfo(extra_field.getText().toString());

        // Create GeoPtMessage to store lat and lng
        GeoPtMessage loc = new GeoPtMessage();
        loc.setLat(Double.valueOf(lat_field.getText().toString()));
        loc.setLon(Double.valueOf(lng_field.getText().toString()));

        sz.setLocation(loc);

        // Set the SafeZone as user created
        sz.setIsUserCreated(true);

        this.SQLhelper.addSafeZone(sz, true);
    }

    /********** onClick Listener for the View **********/
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.dialog_add_safezone_hours:
                // Toggle showing or hiding the hours container
                this.hours_cont.setVisibility(hours_cont.isShown() ? View.GONE : View.VISIBLE);
                this.hours_label.setText(hours_cont.isShown() ? R.string.dialog_add_safezone_hours_collapse : R.string.dialog_add_safezone_hours_expand);
                break;
        }
    }
}
