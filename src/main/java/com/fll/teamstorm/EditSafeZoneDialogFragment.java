package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by coreyja on 1/27/14.
 */
public class EditSafeZoneDialogFragment extends SafeZoneDialogFragment {

    private SafeZone sz;

    public EditSafeZoneDialogFragment(SafeZone sz) {
        super();

        // Set the SafeZone to edit
        this.sz = sz;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Init the texts field needed
        this.titleText = getString(R.string.dialog_edit_safezone_title);
        this.positiveButtonText = getString(R.string.dialog_edit_safezone_positive_text);
    }

    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = super.buildDialog();

        title_field.setText(sz.getTitle());
        lat_field.setText(Double.toString(sz.getLocation().getLat()));
        lng_field.setText(Double.toString(sz.getLocation().getLon()));
        curr_cap_field.setText(Long.toString(sz.getOccupancy()));
        max_cap_field.setText(Long.toString(sz.getMaxOccupancy()));
        phone_field.setText(sz.getPhone());
        extra_field.setText(sz.getExtraInfo());

        return builder;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        // Get the info from the fields
        this.loadSafeZoneFromDialog(this.sz);

        // Edit the given SafeZone
        this.sqlAsync.editSafeZone(this.sz);
    }
}
