package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Rodry on 19/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public LayoutInflater inflater;
    CustomOnClick customOnClick;

    public RecyclerViewAdapter(Context context, CustomOnClick customOnClick){
        this.inflater = LayoutInflater.from(context);
        this.customOnClick = customOnClick;
    }


    public RecyclerViewAdapter.ViewHolder onCreateViewHolder
            (ViewGroup viewGroup, int type) {

        View view = inflater.inflate(R.layout.layout_row,viewGroup,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }


    public void onBindViewHolder
            (RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.NombreRuta.setText(RoutesHolder.getRoutes().get(i).getNombre());

    }


    public int getItemCount() {
        return RoutesHolder.getRoutes().size();
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

            customOnClick.onClickEvent(getLayoutPosition());
        }
    }
}
