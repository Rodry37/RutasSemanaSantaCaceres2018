package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;


import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;

import java.util.ArrayList;

/**
 * Created by Rodry on 23/05/2018.
 */

public interface IList {
    interface View{
        void showErrorConn();
        void showErrorReq();
        void showRoutes(ArrayList<Ruta> rutas);
        void setAdapterRoutes(ArrayList<Ruta> rutas);
    }

    interface Presenter {
        void loadData();
    }
}
