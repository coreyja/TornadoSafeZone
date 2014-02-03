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
import com.fll.teamstorm.Utils;

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

        this.mondayOpen.setText(Utils.formatTimeString(this.hours.getMonOpen()));
        this.mondayClose.setText(Utils.formatTimeString(this.hours.getMonClose()));

        this.tuesdayOpen.setText(Utils.formatTimeString(this.hours.getTueOpen()));
        this.tuesdayClose.setText(Utils.formatTimeString(this.hours.getTueClose()));

        this.wednesdayOpen.setText(Utils.formatTimeString(this.hours.getWedOpen()));
        this.wednesdayClose.setText(Utils.formatTimeString(this.hours.getWedClose()));

        this.thursdayOpen.setText(Utils.formatTimeString(this.hours.getThursOpen()));
        this.thursdayClose.setText(Utils.formatTimeString(this.hours.getThursClose()));

        this.fridayOpen.setText(Utils.formatTimeString(this.hours.getFriOpen()));
        this.fridayClose.setText(Utils.formatTimeString(this.hours.getFriClose()));

        this.saturdayOpen.setText(Utils.formatTimeString(this.hours.getSatOpen()));
        this.saturdayClose.setText(Utils.formatTimeString(this.hours.getSatClose()));

        this.sundayOpen.setText(Utils.formatTimeString(this.hours.getSunOpen()));
        this.sundayClose.setText(Utils.formatTimeString(this.hours.getSunClose()));
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

            try {
                // If there was text in the field, use it as the Time
                String oldTime = field.getText().toString();
                if (oldTime != null || oldTime.length() != 0) {
                    String parts[] = oldTime.split(" ");

                    if (parts.length != 2) throw new Exception();

                    String timeParts[] = parts[0].split(":");

                    if (timeParts.length != 2) throw new Exception();

                    hour = Integer.parseInt(timeParts[0]);
                    minute = Integer.parseInt(timeParts[1]);

                    // If the time is PM add 12 hours to the hour value
                    if (oldTime.trim().endsWith("PM")) {
                        hour += 12;
                    }
                }

            } catch (Exception e){
                // Something went wrong. Just use the default values
            }

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.format("%02d:%02d:%02d", hourOfDay, minute, 0);

            field.setText(Utils.formatTimeString(time));
        }
    }
}
