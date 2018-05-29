package com.imovil.uo239737.rutassemanasantacaceres2018.Adapters;

/**
 * Created by Rodry on 14/04/2018.
 */


/*
    Custom interface for onClick event in RecyclerView
 */
public interface CustomOnClick {
    //The uri is for identifying the route in the DetailsActivity, since url's are unique
    void onClickEvent(String uri);
}
