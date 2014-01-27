package com.fll.teamstorm.dialogs;

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

import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.fll.teamstorm.R;
import com.fll.teamstorm.SQL.SafeZoneSQLAsync;

/**
 * Created by coreyja on 1/19/14.
 */
public abstract class SafeZoneDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    public static final String TAG = "SZ-DIALOG";

    protected String titleText, positiveButtonText;

    protected EditText title_field, lat_field, lng_field, curr_cap_field, max_cap_field, phone_field, extra_field;

    protected SafeZoneSQLAsync sqlAsync;

    // The default constructor must set titleText and positiveButtonText
    public SafeZoneDialogFragment() {
        super();
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

        // Set the SafeZone as user created
        sz.setIsUserCreated(true);
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



        return builder;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = this.buildDialog();

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Assuming the activity is MapActivity get the SQLHelper
        try {
            this.sqlAsync = ((HasSQLAsync) activity).getSqlAsync();
        } catch (Exception e) {
            Log.d(TAG, "Could not get SQLAsync from calling activity.");
        }
    }

    @Override
    public abstract void onClick(DialogInterface dialogInterface, int i);

    public interface HasSQLAsync {

        public SafeZoneSQLAsync getSqlAsync();

    }

}
