package com.imovil.uo239737.rutassemanasantacaceres2018;

import java.util.ArrayList;

/**
 * Created by Rodry on 20/03/2018.
 */

public class RoutesHolder {
    private static ArrayList<Ruta> list;
    public static ArrayList<Ruta> getRoutes() {
        if(list == null)
            list = new ArrayList<Ruta>();

        return list;
    }

    public static void setRoutes(ArrayList<Ruta> rutas) {RoutesHolder.list = rutas;}
}
