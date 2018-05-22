package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail.DetailsActivity;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;
import com.imovil.uo239737.rutassemanasantacaceres2018.Settings.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CustomOnClick {

    FragmentManager fragmentManager;
    ListFragment fragment;
    ArrayList<Ruta> rutas;
    SharedPreferences prefs;
    private final String url = "http://opendata.caceres.es/GetData/GetData?dataset=om:RutaProcesion&year=2018&format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        loadRoutesFromJSON();


        fragmentManager = getSupportFragmentManager();
        fragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragment);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void loadRoutesFromJSON() {
        if (isOnline()) {
            if (RoutesHolder.getRoutes().size() == 0) {

                rutas = new ArrayList<>();
                RequestQueue queue = Volley.newRequestQueue(this);
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
                                ;

                                rutas.add(r);
                            }
                            RoutesHolder.setRoutes(fragment.sortRoutes(rutas));

                            CharSequence mens = getString(R.string.toast_load_ok);
                            Toast t = Toast.makeText(getApplicationContext(), mens, Toast.LENGTH_SHORT);
                            t.show();
                            fragment.updateAdapter();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.print("Error: onResponse()");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CharSequence mens = getString(R.string.toast_load_fail);
                        Toast t = Toast.makeText(getApplicationContext(), mens, Toast.LENGTH_SHORT);
                        t.show();
                        /*
                        ArrayList<Ruta> rerr = new ArrayList<>();
                        Ruta ro = new Ruta();
                        ro.setNombre("asd");
                        ro.setLugar_salida("asdf");
                        ro.setLugar_llegada("fdsa");
                        ro.setUri("ururuu");
                        ArrayList<String> pasos = new ArrayList<>();
                        pasos.add("Paso0");
                        pasos.add("Paso1");
                        pasos.add("Paso2");
                        pasos.add("Paso3");
                        ro.setPasos_Asociados(pasos);
                        rerr.add(ro);
                        RoutesHolder.setRoutes(rerr);
                        fragment.updateAdapter();
                        */
                    }
                });
                queue.add(req);
            }
        } else {
            CharSequence mens = getString(R.string.toast_no_network);
            Toast t = Toast.makeText(getApplicationContext(), mens, Toast.LENGTH_SHORT);
            t.show();
        }
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


    @Override
    public void onClickEvent(int pos) {
        /*
        TWO PANES
        DetailsFragment fragment = DetailsFragment.newInstance(pos);
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
        */
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.RUTA, pos);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() ==  R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
