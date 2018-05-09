package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {
    private static final String ROUTE_ARG = "route";
    int position;

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


        return rootview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
