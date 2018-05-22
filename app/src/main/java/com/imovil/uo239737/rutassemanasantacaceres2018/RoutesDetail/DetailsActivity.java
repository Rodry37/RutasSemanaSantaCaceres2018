package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.imovil.uo239737.rutassemanasantacaceres2018.R;

public class DetailsActivity extends AppCompatActivity {
    int position;
    public static final String RUTA = "RUTA";
    private DetailsPresenter detailsPresenter;
    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position =  intent.getIntExtra(DetailsActivity.RUTA, 0);
        setContentView(R.layout.detail_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if(findViewById(R.id.fgContainer) != null){
            if (savedInstanceState != null) {
                return;
            }
            DetailsFragment detailsFragment = DetailsFragment.newInstance(position);
            getSupportFragmentManager().beginTransaction().add(R.id.fgContainer, detailsFragment).commit();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}