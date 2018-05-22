package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imovil.uo239737.rutassemanasantacaceres2018.R;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Rodry on 19/03/2018.
 */

public class ListFragment extends Fragment  {

    SharedPreferences prefs;
    RecyclerViewAdapter adapter;
    private CustomOnClick customOnClick;

    public static ListFragment newInstance() {

        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Activity activity = getActivity();
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        if(activity != null) {
            try {
                customOnClick = (CustomOnClick) activity;
                adapter = new RecyclerViewAdapter(context, customOnClick);
            } catch (ClassCastException e) {
                Log.e("MYAPP", "Error in onAttach List Fragment");
                e.printStackTrace();
            }
        }
        else{
            Log.e("MYAPP", "Error en onattach de list fragment: Activity null");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvItems);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return rootView;
    }

    @Override
    public void onResume() {
        RoutesHolder.setRoutes(sortRoutes(RoutesHolder.getRoutes()));
        updateAdapter();
        super.onResume();
    }


    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Ruta> sortRoutes(ArrayList<Ruta> rutas){
        String sortmode = "Nombre"; //Default
        try{
            sortmode = prefs.getString("list_preference_order", "Nombre");
        }
        catch(Exception e){
            System.err.print("Error en el sort del array al cargar prefs");
        }

        Comparator<Ruta> comp;
        if (sortmode.equals("Nombre")){
            comp = new Comparator<Ruta>() {
                @Override
                public int compare(Ruta r1, Ruta r2) {
                    return r1.getNombre().compareTo(r2.getNombre());
                }
            };
        }

        else{
            comp = new Comparator<Ruta>() {
                @Override
                public int compare(Ruta r1, Ruta r2) {
                    return r1.getFecha_salida().compareTo(r2.getFecha_salida());
                }
            };
        }

        Collections.sort(rutas, comp);
        return rutas;
    }

}
