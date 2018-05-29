package com.imovil.uo239737.rutassemanasantacaceres2018.RoutesDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;
import com.imovil.uo239737.rutassemanasantacaceres2018.RoutesMap.MapsActivity;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
Fragment for the Details Activity class
 */
public class DetailsFragment extends Fragment implements Button.OnClickListener, IDetails.View{
    private static final String ROUTE_ARG = "route";
    private DetailsPresenter presenter;
    TextView lbNombre;
    TextView lbSalida;
    TextView lbLlegada;
    TextView lbURI;
    TextView lbFechasalida;
    TextView lbPasos;
    Button btMap;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String uri) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ROUTE_ARG, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        /*
        Data is showed on the onStart method because of it is done
        in the onCreateView it can raise a presenter exception
        */
        if(presenter != null)
            presenter.getData();

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.detail_fragment, container, false);

        if(getArguments() != null) {
            presenter = new DetailsPresenter(this, getArguments().getString(ROUTE_ARG));
            lbNombre = rootview.findViewById(R.id.lbNombre);
            lbSalida = rootview.findViewById(R.id.lbSalida);
            lbLlegada = rootview.findViewById(R.id.lbLlegada);
            lbURI = rootview.findViewById(R.id.lbURI);
            lbFechasalida = rootview.findViewById(R.id.lbFechasalida);
            lbPasos = rootview.findViewById(R.id.lbPasos);
            btMap = rootview.findViewById(R.id.btMap);

            btMap.setOnClickListener(this);
        }
        return rootview;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btMap){
            int position = presenter.getPosition();
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra(MapsActivity.PUNTOS, position);
            startActivity(intent);
        }
    }

    @Override
    public void printRuta(Ruta ruta) {
        lbNombre.setText(ruta.getNombre());
        lbSalida.setText(ruta.getLugar_salida());
        lbLlegada.setText(ruta.getLugar_llegada());
        lbURI.setText(ruta.getUri());
        if(ruta.getFecha_salida().getTime() == 0)
            lbFechasalida.setText(getString(R.string.details_date_invalid));
        else {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            lbFechasalida.setText(dateformat.format(ruta.getFecha_salida()) + " h.");
        }
        lbPasos.setText(presenter.formatPasos(ruta.getPasos_Asociados()));
    }
}
