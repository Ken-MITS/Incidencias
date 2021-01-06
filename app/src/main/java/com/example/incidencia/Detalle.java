package com.example.incidencia;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.incidencia.DB.IncidenciaDBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Detalle extends Fragment {

    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Incidencia> arrayList;
    private ArrayList<String> a;
    private int position;
    TextView titulo, nivel, description, date, status;
    ImageView statusColor;
    String currentStatus;

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
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        titulo = view.findViewById(R.id.detalleTitulo);
        nivel = view.findViewById(R.id.detalleNivel);
        description = view.findViewById(R.id.detalleDesc);
        date = view.findViewById(R.id.detalleDate);
        statusColor = view.findViewById(R.id.detalleStatusColor);
        status = view.findViewById(R.id.status);

        dbHelper= new IncidenciaDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        arrayList = dbHelper.showIncidencias();

        titulo.setText(arrayList.get(position).getNom());
        nivel.setText(arrayList.get(position).getPrioritat());
        description.setText(arrayList.get(position).getDescription());
        date.setText(arrayList.get(position).getDate());

        a= dbHelper.showByDate(arrayList.get(position).getDate());
        currentStatus = a.get(0);
        changeStatus(currentStatus, 0, null);
        Log.i("provaLog", "currentStatus = a.get(0)"+currentStatus);


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d;
                d = String.valueOf(arrayList.get(position).getDate());
                ArrayList<String> a = dbHelper.showByDate(d);
                Log.i("provaLog","a.get(0): "+a.get(0));

                currentStatus = a.get(0);
                changeStatus(currentStatus, 1, d);

            }
        });
        return view;
    }

    public void changeStatus(String currentStatus, int num, String date){
        int orange = getResources().getColor(R.color.orange);
        int green = getResources().getColor(R.color.green);
        if (num==1) {
            Log.i("provaLog", "num=1");
            if (currentStatus.equals(getResources().getString(R.string.status))) {
                status.setText(getResources().getString(R.string.assigned));
                statusColor.setColorFilter(orange);
                currentStatus=getResources().getString(R.string.assigned);
                dbHelper.updateIncidencias(date, currentStatus);
            } else {
                status.setText(getResources().getString(R.string.done));
                statusColor.setColorFilter(green);
                currentStatus=getResources().getString(R.string.done);
                dbHelper.updateIncidencias(date, currentStatus);
            }
            Log.i("provaLog", "currentStatus: "+currentStatus);

        }else if (num==0){
            Log.i("provaLog", "num=0");
            if (currentStatus.equals(getResources().getString(R.string.assigned))) {
                status.setText(getResources().getString(R.string.assigned));
                statusColor.setColorFilter(orange);
            } else if (currentStatus.equals(getResources().getString(R.string.done))){
                status.setText(getResources().getString(R.string.done));
                statusColor.setColorFilter(green);
            }
        }
        db.close();

    }
}