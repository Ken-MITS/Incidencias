package com.example.incidencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        final CheckBox remember = findViewById(R.id.remember);
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remeber", "");
        if (checkbox.equals("true")){
            goToMenu();
        }else if (checkbox.equals("false")){
            Toast.makeText(this,  "Please Sign In.", Toast.LENGTH_SHORT).show();
        }

        final EditText username = findViewById(R.id.TXTUsername);
        final EditText password = findViewById(R.id.TXTPassword);

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
                    //username.setText("");
                    password.setText("");
                }
            }
        });


        remember.setText(getResources().getString(R.string.remember));
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                }else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void goToMenu() {
        Intent i = new Intent(MainActivity.this,Menu.class);
        startActivity(i);
    }

}