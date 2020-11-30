package com.example.incidencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incidencia.DB.IncidenciaDBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_guardar);
        button.setText(getResources().getString(R.string.bLogin));

        final EditText username = findViewById(R.id.TXTUsername);
        final EditText password = findViewById(R.id.TXTPassword);
        final TextView resultado =findViewById(R.id.TXTResultado);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("button clicats", "El botó de login ha sigut clicat");

                //goToMenu();
                String TXTUsername = username.getText().toString();
                String TXTPassword = password.getText().toString();

                if (TXTUsername.equals("admin") && TXTPassword.equals("admin")){
                    //LOGIN OK
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.logging), Toast.LENGTH_LONG).show();
                    goToMenu();
                }else{
                    //LOGIN NO
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }
            }
        });
    }
    public void goToMenu() {
        Intent i = new Intent(MainActivity.this,Menu.class);
        startActivity(i);
    }

}