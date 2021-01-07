package com.example.incidencia.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.incidencia.DB.IncidencaContract.*;
import com.example.incidencia.Incidencia;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.incidencia.DB.IncidencaContract.IncidenciaEntry.COLUMN_NAME_DATE;
import static com.example.incidencia.DB.IncidencaContract.IncidenciaEntry.TABLE_NAME;

public class IncidenciaDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME= "incidencias.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TABLE_NAME+"("
            +IncidenciaEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ IncidenciaEntry.COLUMN_NAME_TITLE+" TEXT, "
            +IncidenciaEntry.COLUMN_NAME_LEVEL+" TEXT, " + IncidenciaEntry.COLUMN_NAME_DESCRIPTION+" TEXT, "
            +IncidenciaEntry.COLUMN_NAME_DATE+" TEXT, " + IncidenciaEntry.COLUMN_NAME_STATUS+" TEXT)" ;

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
            values.put(IncidenciaEntry.COLUMN_NAME_DESCRIPTION, in.getDescription());
            values.put(IncidenciaEntry.COLUMN_NAME_DATE, in.getDate());
            values.put(IncidenciaEntry.COLUMN_NAME_STATUS, in.getStatus());

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
                //Create a new object with the values on the current cursor value. (So it's not really a new object)
                Incidencia i = new Incidencia();
                i.setNom(cursor.getString(1));
                i.setPrioritat(cursor.getString(2));
                i.setDescription(cursor.getString(3));
                i.setDate(cursor.getString(4));
                i.setStatus(cursor.getString(5));

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

    public ArrayList<String> showByDate(String date){
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME+ " where date = \""+ date+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String i = cursor.getString(5);
                arrayList.add(i);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public void updateIncidencias(String date, String status){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IncidenciaEntry.COLUMN_NAME_STATUS, status);

        db.update(TABLE_NAME, values, "date = ?" ,new String[]{date});

        /*String query = "update incidencia set status = \""+ currentStatus+"\" where date = \""+date+"\"";
        db.execSQL(query);*/
        db.close();
    }

    public void deleteOne(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "date = ?", new String[]{date});
        db.execSQL("delete from "+ TABLE_NAME + " where date=\""+date+"\"");
        db.close();

    }

    public ArrayList<Incidencia> showByPriority(String priority){
        ArrayList<Incidencia> a = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME + " where status=\""+priority+"\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                //Create a new object with the values on the current cursor value. (So it's not really a new object)
                Incidencia i = new Incidencia();
                i.setNom(cursor.getString(1));
                i.setPrioritat(cursor.getString(2));
                i.setDescription(cursor.getString(3));
                i.setDate(cursor.getString(4));
                i.setStatus(cursor.getString(5));

                //insert into the returning ArrayList to pass it as the adapter parameter.
                a.add(i);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return a;
    }



}











