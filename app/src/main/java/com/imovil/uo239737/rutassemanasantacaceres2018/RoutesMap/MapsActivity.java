package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;

/*
Activity to show the map
The fragment is created in within this class
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, IMaps.View {

    private GoogleMap mMap;
    public final static String PUNTOS = "Puntos";
    PolylineOptions polyOptions;
    Polyline polyline;
    Marker markerInicio;
    Marker markerFin;
    SharedPreferences prefs;
    IMaps.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_maps);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        presenter = new MapsPresenter(intent.getIntExtra(MapsActivity.PUNTOS, 0), prefs);
        presenter.loadTrazado();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float markerColor = presenter.getMarkercolor();
        int trazadoColor = presenter.getTrazadocolor();

        if(!presenter.getLugarLlegada().equals(presenter.getLugarSalida())){
           markerInicio = mMap.addMarker(new MarkerOptions()
                    .position(presenter.getTrazado().get(presenter.getTrazado().size()-1))
                    .title(getString(R.string.marker_title_end) + presenter.getLugarLlegada())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));

           markerFin = mMap.addMarker(new MarkerOptions()
                    .position(presenter.getTrazado().get(0))
                    .title(getString(R.string.marker_title_start) + presenter.getLugarSalida())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
        }

        else{
            markerInicio = mMap.addMarker(new MarkerOptions()
                    .position(presenter.getTrazado().get(0))
                    .title(getString(R.string.marker_title_startend) + presenter.getLugarLlegada())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(presenter.getTrazado().get(0)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        pintarTrazado(trazadoColor);
    }

    private void pintarTrazado(int trazadoColor) {
        polyOptions = new PolylineOptions();
        for(LatLng coord : presenter.getTrazado())
            polyOptions.add(coord);


        polyOptions.color(trazadoColor);
        polyOptions.width(25);
        polyline = mMap.addPolyline(polyOptions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish(); // close this activity and return to preview activity (if there is any)

        return super.onOptionsItemSelected(item);
    }

}
