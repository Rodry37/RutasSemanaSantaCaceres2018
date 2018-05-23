package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;


/**
 * Created by Rodry on 23/05/2018.
 */

public interface IList {
    interface View{
        void showErrorConn();
        void showErrorReq();
        void showRoutes();
    }

    interface Presenter {
        void loadData();
    }
}
