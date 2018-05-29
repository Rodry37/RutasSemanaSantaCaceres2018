package com.imovil.uo239737.rutassemanasantacaceres2018.Model;

import java.util.ArrayList;

/**
 * Created by Rodry on 20/03/2018.
 */


/*
Singleton class to store the Routes.
A persistent way to store information is not used because this the only data the app manages
and is received every time the app launches via a GET HTTP Method.
Since the .json file downloaded has a very small size (~100KB) it is affordable to download a new file
every time the user launches the app.
With this method we are also ensuring that the user is receiving the newest information available.
 */
public class RoutesHolder {
    private static ArrayList<Ruta> list;
    public static ArrayList<Ruta> getRoutes() {
        if(list == null)
            list = new ArrayList<>();

        return list;
    }

    public static void setRoutes(ArrayList<Ruta> rutas) {
        RoutesHolder.list = rutas;
    }
}
