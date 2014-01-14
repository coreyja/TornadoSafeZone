package com.fll.teamstorm;

import com.appspot.perfect_atrium_421.safezones.model.SafeZone;

import java.util.List;

/**
 * Created by coreyja on 1/14/14.
 */
public interface OnSafeZonesLoadedListener {

    public void onSafeZonesLoaded(List<SafeZone> zones);
}
