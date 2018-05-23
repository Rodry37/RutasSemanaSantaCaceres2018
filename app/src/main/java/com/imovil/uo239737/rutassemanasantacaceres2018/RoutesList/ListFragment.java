package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imovil.uo239737.rutassemanasantacaceres2018.Adapters.CustomOnClick;
import com.imovil.uo239737.rutassemanasantacaceres2018.Adapters.RecyclerViewAdapter;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Rodry on 19/03/2018.
 */

public class ListFragment extends Fragment implements IList.View  {

    SharedPreferences prefs;
    RecyclerViewAdapter adapter;
    private CustomOnClick customOnClick;
    IList.Presenter presenter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvItems);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new ListPresenter(getActivity(), prefs, this);

        return rootView;
    }

    @Override
    public void onResume() {
        presenter.loadData();
        adapter.notifyDataSetChanged();
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


    @Override
    public void showErrorConn() {
        CharSequence mens = getString(R.string.toast_no_network);
        Toast t = Toast.makeText(getActivity().getApplicationContext(), mens, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void showErrorReq() {
        CharSequence mens = getString(R.string.toast_load_fail);
        Toast t = Toast.makeText(getActivity().getApplicationContext(), mens, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void showRoutes() {
        adapter.notifyDataSetChanged();
        CharSequence mens = getString(R.string.toast_load_ok);
        Toast t = Toast.makeText(getActivity().getApplicationContext(), mens, Toast.LENGTH_SHORT);
        t.show();
    }
}
