package com.example.stask.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stask on 31/05/17.
 */

public class BaseDadesAdapter extends SQLiteOpenHelper {
        private static final String TABLE_NAME = "items";
        public static final String KEY_ROWID = "_id";
        public static final String KEY_TITTLE= "titol";
        public static final String KEY_DATE= "data";
        public static final String KEY_DONE= "fet";
        private static final String TABLE_CREATE= "create table "+TABLE_NAME+" ("+KEY_ROWID+" integer primary key autoincrement, "+KEY_TITTLE+" text not null, "+
                KEY_DATE+" datetime default (datetime(current_timestamp)), "+KEY_DONE+" integer not null" +"\n);";

        private static final String DATABASE_NAME = "data";
        private static final int DATABASE_VERSION = 1;

        private SQLiteDatabase db; // reference to the database object

        public BaseDadesAdapter(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = getWritableDatabase();
        }
        // automatically called when database is created in constructor
        public void onCreate(SQLiteDatabase d) {
            d.execSQL(TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        // called when database is opened
        public void onOpen(SQLiteDatabase d) {
            super.onOpen(d);

        }

        ///////////////////Métodes de puntuaciñó
        public int insertTask(String titol,boolean fet){
            int intfet = (fet)? 1 :0 ;
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_DONE,intfet);
            initialValues.put(KEY_TITTLE,titol);
            return (int) db.insert(TABLE_NAME, null, initialValues);
        }


        public void updateTask(int id, boolean fet) {
            int intfet = (fet)? 1 :0 ;

            String stmt = "update "+TABLE_NAME+" set "+KEY_DONE+"='" + intfet + "' where "+KEY_ROWID+"='" + id +"'";
            db.execSQL(stmt);
        }


        public Cursor returnTask(boolean done) {
            int intfet = (done)? 1 :0 ;
            String stmt =  "select * from "+TABLE_NAME+" where " + KEY_DONE+ " = "+intfet+ " order by "+KEY_DATE+" asc";
            return db.rawQuery(stmt,null);
        }

        public void removeTask(int id){
            String stmt =  "delete from "+TABLE_NAME+" where " + KEY_ROWID+ " = "+id;
            db.execSQL(stmt);
        }


    }
