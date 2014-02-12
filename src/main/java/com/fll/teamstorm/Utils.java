package com.fll.teamstorm;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by coreyja on 12/13/13.
 */
public class Utils {


    // Static method for formatting a phone number.
    // MUST be in format ########## for this method to work correctly. It will check length to verify, but that is all
    public static String formatPhoneNumber(String orig){
        if (orig.length() != 10){
            return orig;
        }

        return String.format("(%s) %s-%s",orig.substring(0,3), orig.substring(3,6), orig.substring(6));
    }

    // Static method for changing a Endpoints Time string to a AM/PM style string
    // Assumes the String is in the format returned by Endpoints
    public static String formatTimeString(String orig){

        if (orig == null) {
            return "";
        }

        String[] parts = orig.split(":");

        // If there are not 3 parts, it's not formatted like expected so just return the orig
        if (parts.length != 3){
            return orig;
        }

        // Get an integer of the hour, minutes and seconds.
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int second = (int) Float.parseFloat(parts[2]); //Second is parsed as a float then casted down to an int

        // Take care of most of the cases for AM vs PM
        boolean isAM = (hour < 12);

        if (!isAM){
            // If the hour is PM subtract 12 from the hour to get the correct hour to display
            hour -= 12;
        }

        // If the hour is 0, that means it is really 12. This should fix both 12's
        if (hour == 0){
            hour = 12;
        }

        String toReturn = String.format("%02d:%02d %s", hour, minute, (isAM)? "AM" : "PM");;

        return toReturn;

    }

    // Returns the distance between the two points in km.
    // Uses the haversine formula to convert
    // Formula taken from http://www.movable-type.co.uk/scripts/latlong.html
    public static double distanceBetweenPoints(LatLng l1, LatLng l2){

        double radius = 6371; // Average Radius of the Earth in km

        double dLat = Math.toRadians(l2.latitude - l1.latitude);
        double dLong = Math.toRadians(l2.longitude - l1.longitude);

        double lat1 = Math.toRadians(l1.latitude);
        double lat2 = Math.toRadians(l2.latitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLong/2) * Math.sin(dLong/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = radius * c;

        return d;

    }

    // Helper method that takes a Location and a LatLng and converts the Location to a LatLng so we can calculate distance between them.
    public static double distanceBetweenPoints(Location l1, LatLng l2){
        return distanceBetweenPoints(new LatLng(l1.getLatitude(), l1.getLongitude()), l2);
    }

    // Simple method to convert km to miles
    public static double kmToMiles(double km){
        return km * 0.621371;
    }
}
