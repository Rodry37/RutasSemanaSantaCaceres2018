package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.imovil.uo239737.rutassemanasantacaceres2018.R;

/*
Activity for showing the details of a selected route.
The uri represent the route we just selected and is recovered by the presenter
 */
public class DetailsActivity extends AppCompatActivity {
    String uri;
    public static final String RUTA = "RUTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uri =  intent.getStringExtra(DetailsActivity.RUTA);
        setContentView(R.layout.detail_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(findViewById(R.id.fgContainer) != null){
            if (savedInstanceState != null)
                return;

            DetailsFragment detailsFragment = DetailsFragment.newInstance(uri);
            getSupportFragmentManager().beginTransaction().add(R.id.fgContainer, detailsFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish(); // close this activity and return to preview activity (if there is any)

        return super.onOptionsItemSelected(item);
    }
}
