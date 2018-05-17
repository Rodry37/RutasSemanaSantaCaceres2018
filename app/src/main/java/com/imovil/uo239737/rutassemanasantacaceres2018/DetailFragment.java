package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailFragment extends Fragment implements Button.OnClickListener{
    private static final String ROUTE_ARG = "route";
    private int position;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int pos) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ROUTE_ARG, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.detail_fragment, container, false);
        Bundle args = this.getArguments();
        if(args != null)
            position = args.getInt(ROUTE_ARG);
        TextView lbNombre = (TextView) rootview.findViewById(R.id.lbNombre);
        TextView lbSalida = (TextView) rootview.findViewById(R.id.lbSalida);
        TextView lbLlegada = (TextView) rootview.findViewById(R.id.lbLlegada);
        TextView lbURI = (TextView) rootview.findViewById(R.id.lbURI);
        TextView lbFechasalida = (TextView) rootview.findViewById(R.id.lbFechasalida);
        TextView lbPasos = (TextView) rootview.findViewById(R.id.lbPasos);
        Button btMap = (Button)  rootview.findViewById(R.id.btMap);

        lbNombre.setText(RoutesHolder.getRoutes().get(position).getNombre());
        lbSalida.setText(RoutesHolder.getRoutes().get(position).getLugar_salida());
        lbLlegada.setText(RoutesHolder.getRoutes().get(position).getLugar_llegada());
        lbURI.setText(RoutesHolder.getRoutes().get(position).getUri());
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lbFechasalida.setText(dateformat.format(RoutesHolder.getRoutes().get(position).getFecha_salida()));
        printPasos(lbPasos);
        btMap.setOnClickListener(this);

        return rootview;
    }

    private void printPasos(TextView lbPasos) {
        String pasosformat = "";
        ArrayList<String> pasos = RoutesHolder.getRoutes().get(position).getPasos_Asociados();
        for (String s : pasos){
            pasosformat+=s + "\n";
        }
        lbPasos.setText(pasosformat);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btMap){
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra(MapsActivity.PUNTOS, position);
            startActivity(intent);
        }
    }
}
