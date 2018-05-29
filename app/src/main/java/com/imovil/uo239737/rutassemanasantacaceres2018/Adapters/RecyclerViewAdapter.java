package com.imovil.uo239737.rutassemanasantacaceres2018.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.imovil.uo239737.rutassemanasantacaceres2018.Model.RoutesHolder;
import com.imovil.uo239737.rutassemanasantacaceres2018.Model.Ruta;
import com.imovil.uo239737.rutassemanasantacaceres2018.R;

import java.util.ArrayList;

/**
 * Created by Rodry on 19/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public LayoutInflater inflater;
    CustomOnClick customOnClick;
    //We are creating two vars for filtering.
    ArrayList<Ruta> rutas;
    ArrayList<Ruta> rutasCopy;

    public RecyclerViewAdapter(Context context, CustomOnClick customOnClick){
        this.inflater = LayoutInflater.from(context);
        this.customOnClick = customOnClick;
        rutas = new ArrayList<>();
        rutasCopy = new ArrayList<>();
    }


    public RecyclerViewAdapter.ViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int type) {

        View view = inflater.inflate(R.layout.layout_row,viewGroup,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }


    public void onBindViewHolder
            (RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.NombreRuta.setText(rutas.get(i).getNombre());

    }

    public void setRutas(ArrayList<Ruta> rutas){
        this.rutas = (ArrayList<Ruta>) rutas.clone();
        if(rutasCopy.size()==0)
            rutasCopy.addAll(rutas);

        notifyDataSetChanged();
    }


    public int getItemCount() {
        return rutas.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView NombreRuta;

        public ViewHolder(View itemView){
            super(itemView);

            NombreRuta = (TextView) itemView.findViewById(R.id.lbNombreRuta);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            customOnClick.onClickEvent(rutas.get(getLayoutPosition()).getUri());
        }
    }

    /*
    Method to filter the elements in the RecyclerView
    rutasCopy hold a a copy of all the elements to display
    rutas is modified to only show the elements that match the filter
     */
    public void filter(String query, String method){
        rutas.clear();
        if(query.isEmpty())
            rutas.addAll(rutasCopy);

        else{
            query = query.toLowerCase();
            switch(method){
                case "Nombre":
                    for(Ruta r : rutasCopy)
                        if(r.getNombre().toLowerCase().contains(query))
                            rutas.add(r);
                    break;

                case "Lugar de Salida":
                    for(Ruta r : rutasCopy)
                        if(r.getLugar_salida().toLowerCase().contains(query))
                            rutas.add(r);
                    break;

                case "Pasos Asociados":
                    for(Ruta r :rutasCopy)
                        for(String paso : r.getPasos_Asociados()) {
                            String[] pasosformatsplitted = paso.split("/");
                            if(pasosformatsplitted[pasosformatsplitted.length - 1].replace('-', ' ').contains(query))
                                rutas.add(r);
                        }
                    break;
            }
        }
        notifyDataSetChanged();
    }

}
