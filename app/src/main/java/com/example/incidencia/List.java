package com.example.incidencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.incidencia.DB.IncidenciaDBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class List extends Fragment {

    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;

    public List() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list, container, false);


        dbHelper= new IncidenciaDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        ArrayList<Incidencia> a;
        a = dbHelper.showIncidencias();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(a, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> spinnerAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.statusFilter, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = (String) spinner.getSelectedItem();
                int optionPosition = spinner.getSelectedItemPosition();
                ArrayList<Incidencia> a = new ArrayList<>();
                //a = dbHelper.showByPriority(option);

                if (optionPosition == 0){
                    a =dbHelper.showIncidencias();
                }else if (optionPosition == 1){
                    a=dbHelper.showByPriority(getResources().getString(R.string.status));
                }else if (optionPosition == 2){
                    a=dbHelper.showByPriority(getResources().getString(R.string.assigned));
                }else if (optionPosition == 3) {
                    a=dbHelper.showByPriority(getResources().getString(R.string.done));
                }

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(a, getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }
        });


        return view;
    }
}

