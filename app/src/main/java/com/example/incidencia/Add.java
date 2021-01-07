package com.example.incidencia;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.incidencia.DB.IncidenciaDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add extends Fragment{
    //Database
    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;

    public Add() {
    }


    public static ArrayList<Incidencia> llistaIncidencies = new ArrayList<Incidencia>();

    public static ArrayList<Incidencia> getLlistaIncidencies(){
        return llistaIncidencies;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View menu = inflater.inflate(R.layout.fragment_add, container, false);

        final Spinner spinner = (Spinner) menu.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.priority, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        final Button buttonAfegir = menu.findViewById(R.id.buttonAfegir);
        buttonAfegir.setText(getResources().getString(R.string.bAdd));

        buttonAfegir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //EditText value into variable <titol> and changing it to string type.
                EditText x = menu.findViewById(R.id.ETnombre);
                String titol = x.getText().toString();

                EditText editText= menu.findViewById(R.id.ETdescription);
                String description = editText.getText().toString();
                Log.i("provaLog", "Description: "+ description);

                //DATE
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String currentDate = simpleDateFormat.format(new Date());
                Log.i("provaLog", "DATE: "+ currentDate);

                //Getting spin value into <option> variable.
                String option = spinner.getSelectedItem().toString();

                //Status
                String status = getResources().getString(R.string.status);
                Log.i("provaLog", "STATUS: "+ status);

                //DB
                dbHelper= new IncidenciaDBHelper(getContext());
                db =dbHelper.getWritableDatabase();
                dbHelper.insertIncidencia(db, new Incidencia(titol, option, description, currentDate, status));

                Log.i("proves", "option SPINNER" + option);
                getFragmentManager().beginTransaction().remove(Add.this).commit();
                //getActivity().onBackPressed();

            }
        });

        return menu;
    }

    @Override
    public void onDestroyView(){
        //dbHelper.close();
        //db.close();
        super.onDestroyView();
    }


}