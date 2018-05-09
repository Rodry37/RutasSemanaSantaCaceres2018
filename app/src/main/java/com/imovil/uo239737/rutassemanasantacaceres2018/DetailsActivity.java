package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    int position;
    public static final String RUTA = "RUTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position =  intent.getIntExtra(DetailsActivity.RUTA, 0);
        setContentView(R.layout.detail_activity);

        if(findViewById(R.id.frgDetail) != null){
            if (savedInstanceState != null) {
                return;
            }
            DetailFragment detailFragment = DetailFragment.newInstance(position);
            getSupportFragmentManager().beginTransaction().add(R.id.frgDetail, detailFragment).commit();
        }
    }
}
