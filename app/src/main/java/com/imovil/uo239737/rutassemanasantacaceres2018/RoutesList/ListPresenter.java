package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imovil.uo239737.rutassemanasantacaceres2018.Adapters.RecyclerViewAdapter;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Rodry on 23/05/2018.
 */

public class ListPresenter implements IList.Presenter {
    private SharedPreferences prefs;
    private Activity activity;
    private final String url = "http://opendata.caceres.es/GetData/GetData?dataset=om:RutaProcesion&year=2018&format=json";
    private ArrayList<Ruta> rutas;
    private IList.View view;


    public ListPresenter(Activity activity, SharedPreferences prefs, IList.View view){
        this.activity = activity;
        this.prefs = prefs;
        this.view = view;
    }

    @Override
    public void loadData() {
        loadRoutesFromJSON();
    }



    private void loadRoutesFromJSON() {
        if (isOnline()) {
            if (RoutesHolder.getRoutes().size() == 0) {

                rutas = new ArrayList<>();
                RequestQueue queue = Volley.newRequestQueue(activity);
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("results");
                            JSONArray data = response.getJSONArray("bindings");
                            for (int i = 0; i < data.length(); i++) {
                                Ruta r = new Ruta();
                                r.setUri(data.getJSONObject(i).getJSONObject("uri").getString("value"));
                                r.setNombre(data.getJSONObject(i).getJSONObject("rdfs_label").getString("value"));
                                r.setLugar_salida(data.getJSONObject(i).getJSONObject("om_sitioDeSalida").getString("value"));
                                r.setLugar_llegada(data.getJSONObject(i).getJSONObject("om_sitioDeRecogida").getString("value"));
                                r.setDia(data.getJSONObject(i).getJSONObject("time_day").getString("value"));
                                r.setPasos_Asociados(getPasosJSON(data.getJSONObject(i).getJSONObject("pasosAsociados").getString("value")));
                                setTrazadoJSON(data.getJSONObject(i).getJSONObject("locn_geometry").getString("value"), r);

                                //date has to be inside a try because some objects of the JSONArray do not have the 'time_at' value
                                try {
                                    r.setFecha_salida(dateParser(data.getJSONObject(i).getJSONObject("time_at").getString("value")));
                                } catch (Exception e) {
                                    r.setFecha_salida(new Date(0));
                                }


                                rutas.add(r);
                            }
                            rutas = sortRoutes(rutas);
                            RoutesHolder.setRoutes(rutas);

                            view.showRoutes(rutas);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.print("Error: onResponse()");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.showErrorReq();
                    }
                });
                queue.add(req);
            }
            RoutesHolder.setRoutes(sortRoutes(RoutesHolder.getRoutes()));
            view.setAdapterRoutes(sortRoutes(RoutesHolder.getRoutes()));
        } else {
            view.showErrorConn();
        }
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void setTrazadoJSON(String value, Ruta r) {

        String[] puntosSTR = value.replace("[", "").replace("]", "").split(",");
        for (int i = 0; i < puntosSTR.length; i = i + 2) {
            r.addTrazado(Double.parseDouble(puntosSTR[i + 1]), Double.parseDouble(puntosSTR[i]));
        }
    }

    private ArrayList<String> getPasosJSON(String string) {
        ArrayList<String> pasos = new ArrayList<>();
        String[] pasos_uri = string.split(";");
        for (String s : pasos_uri) {
            pasos.add(s);
        }
        return pasos;

    }

    private ArrayList<Ruta> sortRoutes(ArrayList<Ruta> rutas){
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


    private Date dateParser(String JSONdate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = format.parse(JSONdate);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("Error while parsing date");
        }
        return new Date();
    }


}
