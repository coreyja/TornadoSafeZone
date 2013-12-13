package com.fll.teamstorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LauncherActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // Set the OnClickListeners for all the buttons
        ((Button)findViewById(R.id.map_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.kids_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.teens_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.adults_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.seniors_button)).setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {

            new AboutUsDialogFragment().show(getFragmentManager(), AboutUsDialogFragment.TAG);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        Intent i = null;

        // Add the other views to here when they are implemented
        switch (view.getId()){
            case R.id.map_button:
                i = new Intent(this, MapActivity.class);
                break;

            default:
                // Display a toast saying the activity requested isn't implemented yet
                Toast.makeText(getApplicationContext(), getString(R.string.not_implemented),Toast.LENGTH_SHORT).show();
        }

        if (i != null){
            startActivity(i);
        }

    }

    public class AboutUsDialogFragment extends DialogFragment {

        public static final String TAG = "ABOUT";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.about_title);

            // Add the Idea By title to the message
            String message = getString(R.string.about_idea_by) + "\n";

            // Get the array of all the Team Members and add them to the message.
            String[] teamMems = getResources().getStringArray(R.array.team_members);
            for (String s: teamMems) {
                message += String.format("\t- %s\n", s);
            }

            // Add the development by string
            message += "\n" + getString(R.string.about_development_by);

            // Set the message in the builder
            builder.setMessage(message);

            return builder.create();
        }
    }
}
