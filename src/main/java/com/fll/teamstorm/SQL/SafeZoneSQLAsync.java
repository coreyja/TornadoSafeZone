package com.fll.teamstorm.SQL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.fll.teamstorm.MapActivity;
import com.fll.teamstorm.SafeZonesLoadedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coreyja on 1/27/14.
 *
 * All functions of this class will perform actions on the underlying SQLite database, using Async Tasks.
 *
 * The actual Async tasks are described here as private sub-classes.
 *
 */
public class SafeZoneSQLAsync {

    private SafeZonesLoadedListener safeZonesLoadedListener;

    private SafeZoneSQLHelper sqlHelper;

    public SafeZoneSQLAsync(MapActivity mapActivity){
        this(mapActivity, mapActivity);
        // If the constructor is given a MapActivity use it as the context and the SafeZonesLoadedListener
    }

    public SafeZoneSQLAsync(Context context, SafeZonesLoadedListener safeZonesLoadedListener) {
        this.safeZonesLoadedListener = safeZonesLoadedListener;

        this.sqlHelper = new SafeZoneSQLHelper(context);
    }

    public void open() { this.sqlHelper.open(); }
    public void close() { this.sqlHelper.close(); }

    public void emptyTable() {
        //Run the Async task to empty the table
        new EmptySafeZoneTable().execute();
    }

    public void addSafeZone(SafeZone sz){
        // Don't refresh the SafeZone's after a save by default.
        this.addSafeZone(sz, false);
    }

    // Runs an Async task that updates the SafeZone passed into it
    public void editSafeZone(SafeZone sz){
        if (sz.getId() != null){
            new EditSafeZone().execute(sz);
        } else {
            this.addSafeZone(sz, true);
        }

    }

    // Runs an Async task the deleted the specified SafeZone
    public void deleteSafeZone(long id){
        new DeleteSafeZone().execute(id);
    }

    public void addSafeZone(SafeZone sz, boolean loadFromSQLiteAfterSave){
        // Make a new ArrayList with the one SafeZone so we can use the Async that is already written
        ArrayList<SafeZone> list = new ArrayList<SafeZone>();
        list.add(sz);

        new SaveSafeZonesToSQLite(loadFromSQLiteAfterSave).execute(list);
    }

    public void addSafeZones(List<SafeZone> zones){
        // Don't refresh the SafeZone's after a save by default.
        this.addSafeZones(zones, false);
    }

    public void addSafeZones(List<SafeZone> zones, boolean loadFromSQLiteAfterSave){
        new SaveSafeZonesToSQLite(loadFromSQLiteAfterSave).execute(zones);
    }

    // Runs an Async task that loads SafeZones from the DB and passes them to the listener
    public void loadSafeZones(){
        new LoadAllSafeZones().execute();
    }

    // Runs an Async tasks which loads all the Custom SafeZone's and pass them to the listener
    public void loadCustomSafeZones() {
        new LoadCustomSafeZones().execute();
    }

    // Runs an Async task which deletes all user created SafeZones.
    public void clearUserCreated() {
        new ClearUserCreatedSafeZones().execute();
    }

    // Runs an Async task which deletes all not user created SafeZones.
    public void clearNotUserCreated() {
        new ClearNotUserCreatedSafeZones().execute();
    }


    /********* Async Task Classes *********/

    private class SaveSafeZonesToSQLite extends AsyncTask<List<SafeZone>, Void, Void> {
        private boolean loadFromSQLiteAfterSave = true; // Defaults to true unless given in constructor

        public SaveSafeZonesToSQLite() {
            super();
        }

        // Constructor to save loadFromSQLiteAfterSave if one is given
        public SaveSafeZonesToSQLite(boolean loadFromSQLiteAfterSave){
            this.loadFromSQLiteAfterSave = loadFromSQLiteAfterSave;
        }

        @Override
        protected Void doInBackground(List<SafeZone>... lists) {

            // Run over all the lists that we might be given
            for (List<SafeZone> list : lists){
                // Loop through all the SafeZones and add them to the SQLite DB
                for (SafeZone sz : list){
                    SafeZoneSQLAsync.this.sqlHelper.addSafeZone(sz);
                }

                Log.i(MapActivity.TAG, String.format("Saved %d SafeZones to SQLite.", list.size()));

                // Conditionally load from SQLite if we are supposed to
                if (loadFromSQLiteAfterSave) {
                    SafeZoneSQLAsync.this.safeZonesLoadedListener.loadSafeZones();
                }
            }

            return null;
        }

    }

    // Empty the table by dropping and re-adding it, as an Async task
    private class EmptySafeZoneTable extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            // Drop then create the table again
            SafeZoneSQLAsync.this.sqlHelper.emptyTable();

            Log.i(MapActivity.TAG, "Dropped and re-added SafeZone table.");

            return null;
        }
    }

    // Get all the SafeZones in the SQLite DB and pass the list along to the OnSafeZoneLoadedListener
    private class LoadAllSafeZones extends AsyncTask<Void, Void, List<SafeZone>>{

        @Override
        protected List<SafeZone> doInBackground(Void... voids) {

            return SafeZoneSQLAsync.this.sqlHelper.getSafeZones();
        }

        @Override
        protected void onPostExecute(List<SafeZone> result){
            super.onPostExecute(result);

            // Didn't complete successfully
            if (result == null) {
                Log.d(MapActivity.TAG, "Failed Loading from SQLite DB failed.");

                return;
            }

            Log.i(MapActivity.TAG, String.format("%d SafeZones loaded from SQLite.", result.size()));

            SafeZoneSQLAsync.this.safeZonesLoadedListener.onSafeZonesLoaded(result);
        }
    }

    // Get all the Custom SafeZones in the SQLite DB and pass the list along to the OnSafeZoneLoadedListener
    private class LoadCustomSafeZones extends AsyncTask<Void, Void, List<SafeZone>>{

        @Override
        protected List<SafeZone> doInBackground(Void... voids) {

            return SafeZoneSQLAsync.this.sqlHelper.getCustomSafeZones();
        }

        @Override
        protected void onPostExecute(List<SafeZone> result){
            super.onPostExecute(result);

            // Didn't complete successfully
            if (result == null) {
                Log.d(MapActivity.TAG, "Failed: Loading from SQLite DB failed.");

                return;
            }

            Log.i(MapActivity.TAG, String.format("%d Custom SafeZones loaded from SQLite.", result.size()));

            // If we are loading Custom SafeZones this will most likely return to the ListCustomSZ activity
            SafeZoneSQLAsync.this.safeZonesLoadedListener.onSafeZonesLoaded(result);
        }
    }

    private class ClearUserCreatedSafeZones extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            SafeZoneSQLAsync.this.sqlHelper.clearUserCreated();

            return null;
        }
    }

    private class ClearNotUserCreatedSafeZones extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            SafeZoneSQLAsync.this.sqlHelper.clearNotUserCreated();

            return null;
        }
    }

    private class EditSafeZone extends AsyncTask<SafeZone, Void, Void> {

        @Override
        protected Void doInBackground(SafeZone... zones) {

            // Run over all the lists that we might be given
            for (SafeZone sz : zones){

                long id = sz.getId();
                int status = SafeZoneSQLAsync.this.sqlHelper.editSafeZone(id, sz);

                Log.i(MapActivity.TAG, String.format("Updated SZ with ID:%d Status:%d", id, status));

            }

            // Load all the SafeZones so that the zones update after the edit.
            SafeZoneSQLAsync.this.safeZonesLoadedListener.loadSafeZones();

            return null;
        }

    }

    private class DeleteSafeZone extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... ids) {

            // Run over all the lists that we might be given
            for (Long id : ids){

                int status = SafeZoneSQLAsync.this.sqlHelper.deleteSafeZone(id);

                Log.i(MapActivity.TAG, String.format("Deleted SZ with ID:%d Status:%d", id, status));

            }

            // Load all the SafeZones so that the zones update after the delete
            SafeZoneSQLAsync.this.safeZonesLoadedListener.loadSafeZones();

            return null;
        }

    }
}
