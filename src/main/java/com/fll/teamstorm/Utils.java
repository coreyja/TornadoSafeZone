package com.fll.teamstorm;

import android.util.Log;

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
}
