package com.example.applicazione;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseDieta extends SQLiteOpenHelper {


    public DataBaseDieta(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "diete.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE DIETE ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NUM INTEGER, IDCIBI TEXT, QUANTITA TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean addOne (Dieta dieta){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ID",dieta.getId());
        cv.put("NUM",dieta.getNumElem());
        cv.put("IDCIBI", dieta.IdToString());
        cv.put("QUANTITA",dieta.QtaToString());


        long insert = db.insert("DIETE", null, cv);

        return insert != -1;


    }




}
