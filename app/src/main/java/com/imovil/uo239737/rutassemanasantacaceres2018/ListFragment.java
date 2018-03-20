package com.imovil.uo239737.rutassemanasantacaceres2018;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Rodry on 19/03/2018.
 */

public class ListFragment extends Fragment {
    RecyclerViewAdapter adapter;

    public static ListFragment newInstance() {

        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        adapter = new RecyclerViewAdapter(context);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvItems);
        adapter = new RecyclerViewAdapter(this.getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

}
