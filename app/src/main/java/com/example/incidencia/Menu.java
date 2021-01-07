package com.example.incidencia;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


//import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.incidencia.DB.IncidenciaDBHelper;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    ImageView settings;
    public static boolean segundoFragment=false;

    //Dialog
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //Dialog
        builder = new AlertDialog.Builder(this);

        ImageView bAdd = findViewById(R.id.bAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("proves", "button afegir click.");

                FragmentManager menuManager = getSupportFragmentManager();
                Fragment add = new Add();

                //FragmentTransaction menuTransaction =menuManager.beginTransaction();
                //menuTransaction.replace(R.id.mainFragment, add);
                //menuTransaction.commit();
                menuManager.beginTransaction().replace(R.id.mainFragment, add).commit(); //Equivale a las 3 anteriores.
                settings.setVisibility(View.INVISIBLE);
            }
        });


        ImageView bList = findViewById(R.id.bList);
        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                Fragment list = new List();
                menuManager.beginTransaction().replace(R.id.mainFragment, list).commit();
                settings.setVisibility(View.INVISIBLE);
            }
        });

        dbHelper= new IncidenciaDBHelper(getBaseContext());
        db =dbHelper.getWritableDatabase();

        ImageView bDelete = findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("provaLog", "Ha entrado en el bDelete!");

                //Builds dialog box but does not create!
                builder.setMessage(getResources().getString(R.string.dialogMessage))
                        .setCancelable(true)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deleteIncidencias();

                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast), Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                //Create dialog box
                AlertDialog alertDialog= builder.create();
                //Setting the title
                alertDialog.setTitle(getResources().getString(R.string.dialogTitle));
                //Without this nothing come up
                alertDialog.show();
            }
        });

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                Fragment ajustes = new Ajustes();
                menuManager.beginTransaction().replace(R.id.mainFragment, ajustes).addToBackStack(null).commit();

                settings.setVisibility(View.INVISIBLE);

            }
        });

        ImageView help= findViewById(R.id.bHelp);



    }

    @Override
    public void onClick(View v) {

    }

    public void onBackPressed() {
        /*Intent i = new Intent(getApplicationContext(),Menu.class);
        startActivity(i);*/
        Log.i("provaLog", "segundoFragment en metodo: "+segundoFragment);

        if (segundoFragment){
            Log.i("provaLog", "pasa por el onBackPressed cuando segundoFragment=true.");
            segundoFragment=false;
            FragmentManager menuManager = getSupportFragmentManager();
            Fragment list = new List();
            menuManager.beginTransaction().replace(R.id.mainFragment, list).commit();
            settings.setVisibility(View.INVISIBLE);
            return;
        }else {
            Log.i("provaLog", "Pasa por el ONBACKPRESSED");
            finish();
            startActivity(getIntent());

        }
    }
}

