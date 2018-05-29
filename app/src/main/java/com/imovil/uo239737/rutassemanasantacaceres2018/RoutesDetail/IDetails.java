package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail;

import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;

import java.util.ArrayList;

/**
 * Created by Rodry on 22/05/2018.
 */

/*
This represets the contract with the View and the Presenter
 */
public interface IDetails {
    interface View{
        void printRuta(Ruta ruta);
    }

    interface Presenter{
        String formatPasos(ArrayList<String> pasos);
        void getData();
        int getPosition();
    }
}
