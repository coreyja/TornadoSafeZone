package com.fll.teamstorm;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by coreyja on 1/27/14.
 */
public class AddressToLatLngHelper {

    public interface OnLatLngFound {
        public void OnLatLngFound(LatLng coords);
    }

    private OnLatLngFound onLatLngFoundListener;

    public AddressToLatLngHelper(OnLatLngFound onLatLngFoundListener){
        this.onLatLngFoundListener = onLatLngFoundListener;
    }

    public void convertAddressToLatLng(String addr) {
        new GeocodioAsync().execute(addr);

    }

    private class GeocodioAsync extends AsyncTask<String, Void, LatLng>{

        private static final String API_KEY = "6ae724ce15586481c62ae5c442d3dae71ee7440";
        private static final String GEOCODIO_BASE_URL = "http://api.geocod.io/v1/geocode?q=%s&api_key=%s";

        @Override
        protected LatLng doInBackground(String... addresses) {
            if (addresses.length == 0) return null;

            String addr = addresses[0];

            try {
                addr =  URLEncoder.encode(addr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Failed to Encode address to url", e);
            }

            String url = String.format(GEOCODIO_BASE_URL, addr, API_KEY);

            Log.i("fll-ts-url", url);

            JSONObject json = RestClient.connect(url), result;
            JSONArray results = null;
            LatLng coords = null;

            try {
                results = json.getJSONArray("results");
                result = results.getJSONObject(0);
                result = result.getJSONObject("location");

                coords = new LatLng(result.getDouble("lat"), result.getDouble("lng"));

                Log.i("fll-ts-coords", coords.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return coords;

        }

        @Override
        protected void onPostExecute(LatLng result){
            if (result == null) {
                Toast.makeText((Context) AddressToLatLngHelper.this.onLatLngFoundListener, "Couldn't get coordinates for the address given.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call the listener when you are done
            onLatLngFoundListener.OnLatLngFound(result);
        }
    }
}
