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

/*
Fragment for the Main Activity which hold the RecyclerViewAdapter
 */
public class ListFragment extends Fragment implements IList.View  {

    private SharedPreferences prefs;
    private RecyclerViewAdapter adapter;
    private CustomOnClick customOnClick;
    private IList.Presenter presenter;

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

        RecyclerView recyclerView = rootView.findViewById(R.id.rvItems);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new ListPresenter(getActivity(), prefs, this);

        return rootView;
    }

    @Override
    public void onResume() {
        /*
        We lead the data every time the fragment is shown to ensure
        that the data is shown correctly
         */
        presenter.loadData();
        super.onResume();
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
    public void showRoutes(ArrayList<Ruta> rutas) {
        setAdapterRoutes(rutas);
        CharSequence mens = getString(R.string.toast_load_ok);
        Toast t = Toast.makeText(getActivity().getApplicationContext(), mens, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void setAdapterRoutes(ArrayList<Ruta> rutas) {
        adapter.setRutas(rutas);
    }

    public RecyclerViewAdapter getAdapter(){
        return adapter;
    }
}
