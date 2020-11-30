package com.example.incidencia.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.incidencia.DB.IncidencaContract.*;
import com.example.incidencia.Incidencia;

import java.util.ArrayList;

import static com.example.incidencia.DB.IncidencaContract.IncidenciaEntry.TABLE_NAME;

public class IncidenciaDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME= "incidencias.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TABLE_NAME+"("
            +IncidenciaEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ IncidenciaEntry.COLUMN_NAME_TITLE+" TEXT, " +
            IncidenciaEntry.COLUMN_NAME_LEVEL +" TEXT)";

    public IncidenciaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIncidencia(SQLiteDatabase db, Incidencia in){
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(IncidenciaEntry.COLUMN_NAME_TITLE, in.getNom());
            values.put(IncidenciaEntry.COLUMN_NAME_LEVEL, in.getPrioritat());

            db.insert(TABLE_NAME, null, values);
        }else{
            Log.i("provaLog", "Database is closed.");
        }
    }

    public ArrayList<Incidencia> showIncidencias(){
        ArrayList<Incidencia> a = new ArrayList<>();

        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Log.i("provaLog", "cursor getColumName 1: "+ cursor.getColumnName(1));
                Log.i("provaLog", "cursor getString 1: "+ cursor.getString(1));
                Log.i("provaLog", "cursor getColumName 2: "+ cursor.getColumnName(2));
                Log.i("provaLog", "cursor getString 2: "+ cursor.getString(2));

                //Create a new object with the values on the current cursor value. (So it's not really a new object)
                Incidencia i = new Incidencia();
                i.setNom(cursor.getString(1));
                i.setPrioritat(cursor.getString(2));

                //insert into the returning ArrayList to pass it as the adapter parameter.
                a.add(i);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return a;
    }

    public void deleteIncidencias(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();

    }

}











