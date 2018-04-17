package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Ruta ruta = (Ruta) intent.getSerializableExtra("RUTA");
        setContentView(R.layout.detail_activity);

        if(findViewById(R.id.frgDetail) != null){
            if (savedInstanceState != null) {
                return;
            }
            DetailFragment detailFragment = DetailFragment.newInstance(ruta);
        }
    }
}
