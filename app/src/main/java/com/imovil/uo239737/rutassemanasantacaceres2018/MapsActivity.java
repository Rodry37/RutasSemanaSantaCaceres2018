package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public final static String PUNTOS = "Puntos";
    private int position;
    private ArrayList<LatLng> trazado;
    PolylineOptions polyOptions;
    Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        position =  intent.getIntExtra(MapsActivity.PUNTOS, 0);
        loadGeolocalisation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadGeolocalisation() {
        trazado = new ArrayList<>();
        for(Punto p : RoutesHolder.getRoutes().get(position).getTrazado()){
            LatLng coord = new LatLng(p.getLatitud(), p.getLongitud());
            trazado.add(coord);
        }
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

        mMap.addMarker(new MarkerOptions()
                .position(trazado.get(0))
                .title(getString(R.string.marker_title_start) + RoutesHolder.getRoutes().get(position).getLugar_llegada())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        if(RoutesHolder.getRoutes().get(position).getLugar_llegada() != RoutesHolder.getRoutes().get(position).getLugar_salida()){
            mMap.addMarker(new MarkerOptions()
                    .position(trazado.get(trazado.size()-1))
                    .title(getString(R.string.marker_title_end) + RoutesHolder.getRoutes().get(position).getLugar_llegada())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

            mMap.addMarker(new MarkerOptions()
                    .position(trazado.get(0))
                    .title(getString(R.string.marker_title_start) + RoutesHolder.getRoutes().get(position).getLugar_llegada())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        }

        else{
            mMap.addMarker(new MarkerOptions()
                    .position(trazado.get(0))
                    .title(getString(R.string.marker_title_startend) + RoutesHolder.getRoutes().get(position).getLugar_llegada())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(trazado.get(0)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        pintarTrazado();
    }

    private void pintarTrazado() {
        polyOptions = new PolylineOptions();
        for(LatLng coord : trazado){
            polyOptions.add(coord);
        }
        polyOptions.color(0xff660066);
        polyOptions.width(25);
        polyline = mMap.addPolyline(polyOptions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
