package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.app.SearchManager;
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
import android.support.v7.widget.SearchView;
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
import com.imovil.uo239737.rutassemanasantacaceres2018.Adapters.CustomOnClick;
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
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        fragmentManager = getSupportFragmentManager();
        fragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragment);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragment.getAdapter().filter(query, prefs.getString("list_preference_filter", "Nombre"));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fragment.getAdapter().filter(newText, prefs.getString("list_preference_filter", "Nombre"));
                return true;
            }
        });

        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchView.setQuery("", false);
        searchView.setIconified(true);
    }

    @Override
    public void onClickEvent(String uri) {
        /*
        TWO PANES
        DetailsFragment fragment = DetailsFragment.newInstance(pos);
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
        */
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.RUTA, uri);
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
