package com.fll.teamstorm.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.fll.teamstorm.R;
import com.fll.teamstorm.SQL.SafeZoneSQLAsync;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by coreyja on 1/27/14.
 */
public class SafeZoneDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    public static final String TAG = "FLL-TS-SZ-DIALOG";

    private String titleText, positiveButtonText;

    private EditText title_field, lat_field, lng_field, curr_cap_field, max_cap_field, phone_field, extra_field;

    private SafeZoneSQLAsync sqlAsync;

    private SafeZone sz;

    public SafeZoneDialogFragment() {
        super();

        this.sz = new SafeZone();
        this.sz.setIsUserCreated(true); // We are creating a new SafeZone so it must be user created
    }

    public SafeZoneDialogFragment(LatLng latLng){
        this();

        this.sz.setLocation(new GeoPtMessage().setLat(latLng.latitude).setLon(latLng.longitude));
    }

    public SafeZoneDialogFragment(SafeZone sz) {
        super();

        // Set the SafeZone to edit
        this.sz = sz;
    }

    // Gets the fields for the SZ from the dialog, and save the info to the provided SafeZone
    public void loadSafeZoneFromDialog(SafeZone sz){

        sz.setTitle(title_field.getText().toString());

        // Check if the occupancy and max occupancy fields are filled. If so save the info, else set them to null
        String occ = curr_cap_field.getText().toString();
        if (!occ.isEmpty()) {
            sz.setOccupancy(Long.valueOf(occ));
        } else {
            sz.setOccupancy(null);
        }
        String max_occ = max_cap_field.getText().toString();
        if (!max_occ.isEmpty()) {
            sz.setMaxOccupancy(Long.valueOf(occ));
        } else {
            sz.setMaxOccupancy(null);
        }

        sz.setPhone(phone_field.getText().toString());
        sz.setExtraInfo(extra_field.getText().toString());

        // Create GeoPtMessage to store lat and lng
        GeoPtMessage loc = new GeoPtMessage();
        loc.setLat(Double.valueOf(lat_field.getText().toString()));
        loc.setLon(Double.valueOf(lng_field.getText().toString()));

        sz.setLocation(loc);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = this.buildDialog();

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Init the texts field needed
        this.titleText = getString(R.string.dialog_edit_safezone_title);
        this.positiveButtonText = getString(R.string.dialog_edit_safezone_positive_text);
    }

    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(this.titleText);

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

        builder.setPositiveButton(this.positiveButtonText, this);

        // The callback can be null, cause all we need to do it close the dialog.
        builder.setNeutralButton(R.string.string_cancel, null);

        title_field.setText(sz.getTitle());

        if (sz.getLocation() != null && !sz.getLocation().isEmpty()) {
            lat_field.setText(Double.toString(sz.getLocation().getLat()));
            lng_field.setText(Double.toString(sz.getLocation().getLon()));
        }

        if (sz.hasOccupancy()) {
            curr_cap_field.setText(Long.toString(sz.getOccupancy()));
        }

        if (sz.hasMaxOccupancy()) {
            max_cap_field.setText(Long.toString(sz.getMaxOccupancy()));
        }

        phone_field.setText(sz.getPhone());
        extra_field.setText(sz.getExtraInfo());

        return builder;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        // Get the info from the fields
        this.loadSafeZoneFromDialog(this.sz);

        new HoursDialog(this.sz).show(getFragmentManager(), HoursDialog.TAG);
    }

    public interface HasSQLAsync {

        public SafeZoneSQLAsync getSqlAsync();

    }
}
