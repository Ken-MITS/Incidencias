package com.example.incidencia.DB;

import android.provider.BaseColumns;

public class IncidencaContract {
    private void IncidenciaContract(){}
    public static class IncidenciaEntry implements BaseColumns{
        public static final String TABLE_NAME="incidencia";
        public static final String ID="id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LEVEL = "level";
        //Nuevos dos campos
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE = "date";


    }
}
