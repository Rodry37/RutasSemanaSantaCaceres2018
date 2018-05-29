package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.imovil.uo239737.rutassemanasantacaceres2018.Adapters.CustomOnClick;
import com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail.DetailsActivity;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;
import com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail.DetailsFragment;
import com.imovil.uo239737.rutassemanasantacaceres2018.Settings.SettingsActivity;

/*
First activity to create when the app is launching
 */
public class MainActivity extends AppCompatActivity implements CustomOnClick {

    FragmentManager fragmentManager;
    ListFragment fragment;
    SharedPreferences prefs;
    SearchView searchView;
    boolean twoPanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Checking if it's a tablet or a smartphone
        if (findViewById(R.id.frame_two_panes) != null) {
            twoPanes = true;
        }
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        fragmentManager = getSupportFragmentManager();
        if(twoPanes)
            fragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragment_list_2panes);

        else
            fragment = (ListFragment) fragmentManager.findFragmentById(R.id.fragment);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        //SearchManager for the filter
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Whenever a char is typed, it filters the list by the method selected in prefs
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

        if(twoPanes) {
            DetailsFragment fragment = DetailsFragment.newInstance(uri );
            fragmentManager.beginTransaction().replace(R.id.frame_two_panes, fragment).commit();
        }
        else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RUTA, uri);
            startActivity(intent);
        }
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
