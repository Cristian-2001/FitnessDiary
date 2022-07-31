package com.example.applicazione;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ValoriNutrizionali2.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Foglio1 ( "
                + "ID REAL, "
                + "NOME VARCHAR(255), "
                + "CATEGORIA VARCHAR(255), "
                + "ENERGIA REAL, "
                + "LIPIDI VARCHAR(255), "
                + "ACIDI_GRASSI_SATURI REAL, "
                + "COLESTEROLO REAL, "
                + "CARBOIDRATI REAL, "
                + "ZUCCHERI REAL, "
                + "FIBRE REAL, "
                + "PROTEINE REAL, "
                + "SALE REAL )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
