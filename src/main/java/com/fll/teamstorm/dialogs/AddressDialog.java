package com.fll.teamstorm.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fll.teamstorm.AddressToLatLngHelper;
import com.fll.teamstorm.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by coreyja on 1/27/14.
 */
public class AddressDialog extends DialogFragment implements AddressToLatLngHelper.OnLatLngFound{

    public static final String TAG = "FLL-TS-DIALOG-ADDRESS";

    private EditText address_field;

    private AddressToLatLngHelper addressHelper;

    private FragmentManager manager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Assuming the activity is MapActivity get the SQLHelper
        try {
            this.addressHelper = new AddressToLatLngHelper(this);
            this.manager = activity.getFragmentManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the layout and add it to the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_address, null);
        builder.setView(v);

        // Store the address_field
        address_field = (EditText) v.findViewById(R.id.dialog_address_address);

        builder.setTitle(getString(R.string.dialog_address_title));

        builder.setPositiveButton(getString(R.string.dialog_address_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String addr = address_field.getText().toString();

                addressHelper.convertAddressToLatLng(addr);
            }
        });

        // If cancel is selected a callback isn't needed as we just close the dialog.
        builder.setNeutralButton(R.string.string_cancel, null);

        return builder.create();
    }

    public void OnLatLngFound(LatLng coords) {
        new SafeZoneDialogFragment(coords).show(this.manager, TAG);
    }
}
