package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesMap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Rodry on 24/05/2018.
 */

/*
This represets the contract with the View and the Presenter
 */

public interface IMaps {
    interface View{}

    interface Presenter {
        void loadTrazado();
        float getMarkercolor();
        int getTrazadocolor();
        ArrayList<LatLng> getTrazado();
        String getLugarSalida();
        String getLugarLlegada();
    }
}
