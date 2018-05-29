package com.imovil.uo239737.rutassemanasantacaceres2018.Model;

/**
 * Created by Rodry on 17/05/2018.
 */

/*
Represent a LatLng position.
Similar to the LatLng object from Google Maps API
 */
public class Punto {
    double Latitud;
    double Longitud;

    public Punto(){
        Latitud = 0;
        Longitud = 0;
    }

    public Punto(double lat, double lng){
        Latitud = lat;
        Longitud = lng;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }
}
