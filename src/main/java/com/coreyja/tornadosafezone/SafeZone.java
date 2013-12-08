package com.coreyja.tornadosafezone;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by coreyja on 12/8/13.
 */
public class SafeZone {

    private LatLng position;
    private String title;
    private String phone;

    /* The hours of the SafeZone. The hours are in 24 hour format. The minutes are just minutes. */
    private int openMinute, openHour;
    private int closeMinute, closeHour;

    private int capacity;

    private String extra_info;


    public SafeZone(LatLng position, String title, String phone, int openHour, int openMinute, int closeHour, int closeMinute, int capacity, String extra_info) {
        this.position = position;
        this.title = title;

        this.phone = phone;
        this.openMinute = openMinute;
        this.openHour = openHour;
        this.closeMinute = closeMinute;
        this.closeHour = closeHour;
        this.capacity = capacity;
        this.extra_info = extra_info;
    }

    public SafeZone(LatLng position, String title, String phone, int openHour, int openMinute, int closeHour, int closeMinute, int capacity) {
        this(position, title, phone, openHour, openMinute, closeHour, closeMinute, capacity, "");
    }

    public SafeZone(LatLng position, String title, String phone, int openHour, int openMinute, int closeHour, int closeMinute) {
        this(position, title, phone, openHour, openMinute, closeHour, closeMinute, 0, "");
    }

    public SafeZone(LatLng position, String title, String phone) {
        this(position, title, phone, -1, 0, -1, 0, 0, "");
    }

    public SafeZone(LatLng position, String title) {
        this(position, title, "", -1, 0, -1, 0, 0, "");
    }


    /* Getters and Setters autogenerated by Android Studio */
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOpenMinute() {
        return openMinute;
    }

    public void setOpenMinute(int openMinute) {
        this.openMinute = openMinute;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseMinute() {
        return closeMinute;
    }

    public void setCloseMinute(int closeMinute) {
        this.closeMinute = closeMinute;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    // Create and return a new MarkerOptions object using the position of this SafeZone
    public MarkerOptions generateMarkerOptions() {
        return new MarkerOptions().position(this.position).title(this.title);
    }

    /* The following return true if the property specified exists for this SafeZone */
    public boolean hasHours() {
        return !( this.openHour == -1 || this.closeHour == -1);
    }
    public boolean hasCapacity() {
        return capacity != 0;
    }
    public boolean hasPhone() {
        return this.phone != "";
    }
    public boolean hasExtraInfo() {
        return this.extra_info != "";
    }

    // Return a formatted string of the hours of this SafeZone
    public String getFormattedHours() {
        String s = "";

        int oh = (openHour > 12) ? openHour-12 : openHour;
        int ch = (closeHour > 12) ? closeHour-12 : closeHour;

        s += oh;
        s += ":" + String.format("%02d", openMinute);
        s += ((openHour > 12) ? " PM " : " AM ");

        s += "to ";
        s += ch + ":" + String.format("%02d", closeMinute);
        s += ((closeHour > 12) ? " PM " : " AM ");

        return s;

    }



}
