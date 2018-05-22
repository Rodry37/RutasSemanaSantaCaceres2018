package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail;

import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;

import java.util.ArrayList;

/**
 * Created by Rodry on 22/05/2018.
 */

public class DetailsPresenter implements IDetails.Presenter {

    private IDetails.View view;
    private int position;

    public DetailsPresenter(IDetails.View view, int position){
        this.view = view;
        this.position = position;
    }


    @Override
    public String formatPasos(ArrayList<String> pasos) {
        String pasosformat = "";
        pasos = RoutesHolder.getRoutes().get(position).getPasos_Asociados();
        for (String s : pasos){
            String[] pasosformatsplitted = s.split("/");
            pasosformatsplitted = pasosformatsplitted[pasosformatsplitted.length-1].replace('-',' ').split(" ");
            for (int i = 0 ; i < pasosformatsplitted.length ; i++)
                if (pasosformatsplitted[i].length() > 3)
                    pasosformatsplitted[i] = pasosformatsplitted[i].substring(0,1)
                            .toUpperCase()
                            .concat(pasosformatsplitted[i].substring(1));


            String pasosformatted = "";
            for(int i = 0 ; i < pasosformatsplitted.length ; i++)
                pasosformatted += pasosformatsplitted[i] + ' ';

            pasosformat+= "-Paso Procesional " + pasosformatted + "\n";
        }
        return pasosformat;
    }

    @Override
    public void getData() {
        Ruta ruta = RoutesHolder.getRoutes().get(position);
        if(view != null)
            view.printRuta(ruta);

    }

    @Override
    public int getPosition() {
        return position;
    }
}
