package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesMap;


import android.content.SharedPreferences;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Punto;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;

import java.util.ArrayList;

/**
 * Created by Rodry on 24/05/2018.
 */

public class MapsPresenter implements IMaps.Presenter {
    private int position;
    private SharedPreferences prefs;
    String color;
    ArrayList<LatLng> trazado;

    public MapsPresenter(int position, SharedPreferences prefs){
        this.position = position;
        this.prefs = prefs;
        color = getMapTheme();
    }

    @Override
    public void loadTrazado() {
        if(trazado == null) {
            trazado = new ArrayList<>();
            for (Punto p : RoutesHolder.getRoutes().get(position).getTrazado()) {
                LatLng coord = new LatLng(p.getLatitud(), p.getLongitud());
                trazado.add(coord);
            }
        }
    }

    @Override
    public float getMarkercolor() {
        switch(color){
            case "Morado":
                return BitmapDescriptorFactory.HUE_MAGENTA;

            case "Rojo":
                return BitmapDescriptorFactory.HUE_RED;

            case "Azul":
                return BitmapDescriptorFactory.HUE_BLUE;

            case "Verde":
                return BitmapDescriptorFactory.HUE_GREEN;

            default:
                System.err.print("Error en el switch de seleccionar color");
                return 0;
        }
    }

    @Override
    public int getTrazadocolor() {
        switch(color){
            case "Morado":
                return 0xff660066;


            case "Rojo":
                return 0xff960000;


            case "Azul":
                return 0xff004789;

            case "Verde":
                return 0xff0b7c2b;

            default:
                System.err.print("Error en el switch de seleccionar color");
                return 0;
        }
    }

    @Override
    public ArrayList<LatLng> getTrazado() {
        return trazado;
    }

    @Override
    public String getLugarSalida() {
        return RoutesHolder.getRoutes().get(position).getLugar_salida();
    }

    @Override
    public String getLugarLlegada() {
        return RoutesHolder.getRoutes().get(position).getLugar_llegada();
    }

    private String getMapTheme(){
        String prefColor = "Morado"; //Default
        try{
            prefColor = prefs.getString("list_preference_color", "Morado");
        }
        catch(Exception e){
            System.err.print("Error al asignar color al trazado");
        }
        return prefColor;
    }


}
