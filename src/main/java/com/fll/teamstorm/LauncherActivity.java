package com.fll.teamstorm;

import android.app.Activity;
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
        if (id == R.id.action_settings) {
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
}
