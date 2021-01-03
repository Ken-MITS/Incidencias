package com.example.incidencia;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.incidencia.DB.IncidenciaDBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Detalle extends Fragment {

    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Incidencia> arrayList;
    private int position;

    public Detalle() {
        // Required empty public constructor
    }
    public Detalle(int pos){
        position=pos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("provaLog", "Dentro fragment detalle");
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        TextView titulo, nivel, description, date;

        titulo = view.findViewById(R.id.detalleTitulo);
        nivel = view.findViewById(R.id.detalleNivel);
        description = view.findViewById(R.id.detalleDesc);
        date = view.findViewById(R.id.detalleDate);

        dbHelper= new IncidenciaDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        arrayList = dbHelper.showIncidencias();
        Log.i("provaLog", "arrayList: "+ arrayList.get(position).getNom());
        titulo.setText(arrayList.get(position).getNom());
        nivel.setText(arrayList.get(position).getPrioritat());
        description.setText(arrayList.get(position).getDescription());
        date.setText(arrayList.get(position).getDate());

        return view;
    }
}