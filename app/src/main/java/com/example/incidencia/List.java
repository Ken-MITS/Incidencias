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
import android.widget.Button;
import android.widget.EditText;

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



        return view;
    }
}

