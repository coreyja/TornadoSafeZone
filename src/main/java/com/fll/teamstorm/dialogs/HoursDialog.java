package com.fll.teamstorm.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appspot.perfect_atrium_421.safezones.model.Hours;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.fll.teamstorm.R;
import com.fll.teamstorm.SQL.SafeZoneSQLAsync;

import java.util.Calendar;

/**
 * Created by coreyja on 1/27/14.
 */
public class HoursDialog extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {

    public static final String TAG = "FLL-TS-DIALOG_HOURS";

    protected SafeZone sz;
    protected Hours hours;

    protected TextView mondayOpen, mondayClose, tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose,
            fridayOpen, fridayClose, saturdayOpen, saturdayClose, sundayOpen, sundayClose;

    protected SafeZoneSQLAsync sqlAsync;

    public HoursDialog(SafeZone sz){
        this.sz = sz;

        if (this.sz.hasHours()){
            this.hours = this.sz.getHours();
        } else {
            this.hours = new Hours();
        }

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = this.buildDialog();

        return builder.create();
    }

    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the layout and add it to the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_hours, null);
        builder.setView(v);

        builder.setTitle(getString(R.string.dialog_hours_title));

        // Init all the Edit Fields
        mondayOpen = (TextView) v.findViewById(R.id.dialog_hours_monday_open);
        mondayClose = (TextView) v.findViewById(R.id.dialog_hours_monday_close);

        tuesdayOpen = (TextView) v.findViewById(R.id.dialog_hours_tuesday_open);
        tuesdayClose = (TextView) v.findViewById(R.id.dialog_hours_tuesday_close);

        wednesdayOpen = (TextView) v.findViewById(R.id.dialog_hours_wednesday_open);
        wednesdayClose = (TextView) v.findViewById(R.id.dialog_hours_wednesday_close);

        thursdayOpen = (TextView) v.findViewById(R.id.dialog_hours_thursday_open);
        thursdayClose = (TextView) v.findViewById(R.id.dialog_hours_thursday_close);

        fridayOpen = (TextView) v.findViewById(R.id.dialog_hours_friday_open);
        fridayClose = (TextView) v.findViewById(R.id.dialog_hours_friday_close);

        saturdayOpen = (TextView) v.findViewById(R.id.dialog_hours_saturday_open);
        saturdayClose = (TextView) v.findViewById(R.id.dialog_hours_saturday_close);

        sundayOpen = (TextView) v.findViewById(R.id.dialog_hours_sunday_open);
        sundayClose = (TextView) v.findViewById(R.id.dialog_hours_sunday_close);

        builder.setPositiveButton(getString(R.string.dialog_hours_positive), this);

        builder.setNeutralButton(getString(R.string.string_cancel), null);

        // Set the On Click Listener of each TextView to this
        mondayOpen.setOnClickListener(this);
        mondayClose.setOnClickListener(this);

        tuesdayOpen.setOnClickListener(this);
        tuesdayClose.setOnClickListener(this);

        wednesdayOpen.setOnClickListener(this);
        wednesdayClose.setOnClickListener(this);

        thursdayOpen.setOnClickListener(this);
        thursdayClose.setOnClickListener(this);

        fridayOpen.setOnClickListener(this);
        fridayClose.setOnClickListener(this);

        saturdayOpen.setOnClickListener(this);
        saturdayClose.setOnClickListener(this);

        sundayOpen.setOnClickListener(this);
        sundayClose.setOnClickListener(this);


        // Get the info from the Hours object and put it in the fields.
        this.loadHoursFromObject();

        return builder;
    }

    private void loadHoursFromDialog() {
        this.hours.setMonOpen(mondayOpen.getText().toString());
        this.hours.setMonClose(mondayClose.getText().toString());

        this.hours.setTueOpen(tuesdayOpen.getText().toString());
        this.hours.setTueClose(tuesdayClose.getText().toString());

        this.hours.setWedOpen(wednesdayOpen.getText().toString());
        this.hours.setWedClose(wednesdayClose.getText().toString());

        this.hours.setThursOpen(thursdayOpen.getText().toString());
        this.hours.setThursClose(thursdayClose.getText().toString());

        this.hours.setFriOpen(fridayOpen.getText().toString());
        this.hours.setFriClose(fridayClose.getText().toString());

        this.hours.setSatOpen(saturdayOpen.getText().toString());
        this.hours.setSatClose(saturdayClose.getText().toString());

        this.hours.setSunOpen(sundayOpen.getText().toString());
        this.hours.setSunClose(sundayClose.getText().toString());
    }

    private void loadHoursFromObject() {
        if (this.hours == null) return;

        this.mondayOpen.setText(this.hours.getMonOpen());
        this.mondayClose.setText(this.hours.getMonClose());

        this.tuesdayOpen.setText(this.hours.getTueOpen());
        this.tuesdayClose.setText(this.hours.getTueClose());

        this.wednesdayOpen.setText(this.hours.getWedOpen());
        this.wednesdayClose.setText(this.hours.getWedClose());

        this.thursdayOpen.setText(this.hours.getThursOpen());
        this.thursdayClose.setText(this.hours.getThursClose());

        this.fridayOpen.setText(this.hours.getFriOpen());
        this.fridayClose.setText(this.hours.getFriClose());

        this.saturdayOpen.setText(this.hours.getSatOpen());
        this.saturdayClose.setText(this.hours.getSatClose());

        this.sundayOpen.setText(this.hours.getSunOpen());
        this.sundayClose.setText(this.hours.getSunClose());
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // Must have clicked on positive button to be here.

        loadHoursFromDialog();

        this.sz.setHours(this.hours);

        this.sqlAsync.editSafeZone(this.sz);

    }

    @Override
    public void onClick(View view) {
        new TimePickerFragment((TextView) view).show(getFragmentManager(), this.TAG);
    }

    private class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private TextView field;

        public TimePickerFragment(TextView field){
            this.field = field;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.format("%02d:%02d:%02d", hourOfDay, minute, 0);

            field.setText(time);
        }
    }
}
