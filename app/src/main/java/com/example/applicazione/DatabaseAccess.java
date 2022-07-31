package com.example.applicazione;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAccess {
    private static final String TAG = "DatabaseAccess";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context) {

        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;

    }

    public void open() {
        this.db = openHelper.getWritableDatabase();

    }

    public void close() {

        if (db != null) {
            this.db.close();
        }
    }

    //metodi per la nostra app

    public String getCarbo(String name) {

        c = db.rawQuery("select CARBOIDRATI from Foglio1 where NOME = '"+ name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();

        Log.d(TAG, "getCarbo: Prima");

        while (c.moveToNext()) {

            String carbo = c.getString(0);
            Log.d(TAG, "getCarbo: A metà: " + carbo);
            buffer.append("" + carbo);

        }

        Log.d(TAG, "getCarbo: Finished: " + buffer.toString());
        return buffer.toString();
    }

}
