package com.example.incidencia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class Ajustes extends Fragment {

    public Ajustes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        loadLocale();

        TextView lang = view.findViewById(R.id.language);
        TextView logOut = view.findViewById(R.id.LogOut);

        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("provaLog", "Language Used: "+getResources().getString(R.string.lang));
                final String[] langList = {getResources().getString(R.string.en), getResources().getString(R.string.sp)};

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.choose));

                builder.setSingleChoiceItems(langList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            setLocale("en");
//                            getFragmentManager().beginTransaction().replace(R.id.settings, Ajustes.class.newInstance()).commit();
                        } else if (which == 1) {
                            setLocale("Default");
//                            getFragmentManager().beginTransaction().detach(getTargetFragment()).attach(getTargetFragment()).addToBackStack(null).commit();
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //getFragmentManager().beginTransaction().detach(getTargetFragment()).attach(getTargetFragment()).addToBackStack(null).commit();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getContext().getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                getActivity().finish();
            }
        });

        return view;
    }


    private void setLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref ="Language";
        SharedPreferences preferences = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(langPref, lang);
        editor.apply();
        editor.commit();

    }

    public void loadLocale(){
        String langPref = "Language";
        SharedPreferences preferences = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        String language = preferences.getString(langPref, "");
        setLocale(language);


    }
}
