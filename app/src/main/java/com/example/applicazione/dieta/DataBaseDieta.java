package com.example.applicazione.dieta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseDieta extends SQLiteOpenHelper {


    public DataBaseDieta(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "diete.db", factory, version);
    }

    public DataBaseDieta(@Nullable Context context) {
        super(context, "diete.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE DIETE ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, NUM INTEGER, IDCIBI TEXT, QUANTITA TEXT)";

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

        //cv.put("ID",dieta.getId());
        cv.put("NOME",dieta.getNome());
        cv.put("NUM",dieta.getNumElem());
        cv.put("IDCIBI", dieta.IdToString());
        cv.put("QUANTITA",dieta.QtaToString());


        long insert = db.insert("DIETE", null, cv);

        return insert != -1;
    }

    public List<Dieta> getAllDiete(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Dieta> returnList = new ArrayList<>();

        String queryString = "SELECT *" + " FROM DIETE";

        Cursor cursor = db.rawQuery(queryString, null);

        Dieta dieta;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                int num = cursor.getInt(2);
                String idCibi = cursor.getString(3);
                String qtaCibi = cursor.getString(4);


                dieta = new Dieta(id, nome, num);

                dieta.setCibiId(dieta.IdToArray(idCibi));

                dieta.setCibiQta(dieta.QtaToArray(qtaCibi));

                returnList.add(dieta);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public Dieta getDietaById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM DIETE WHERE ID = '" + id + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        Dieta dieta = null;

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(1);
            int num = cursor.getInt(2);
            String idCibi = cursor.getString(3);
            String qtaCibi = cursor.getString(4);


            dieta = new Dieta(id, nome, num );

            dieta.setCibiId(dieta.IdToArray(idCibi));

            dieta.setCibiQta(dieta.QtaToArray(qtaCibi));


        }

        cursor.close();
        db.close();
        return dieta;
    }

    public boolean modificaDieta (int id, Dieta dieta){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put("ID",dieta.getId());
        cv.put("NOME",dieta.getNome());
        cv.put("NUM",dieta.getNumElem());
        cv.put("IDCIBI", dieta.IdToString());
        cv.put("QUANTITA",dieta.QtaToString());


        long modify = db.update("DIETE", cv, "ID = ?", new String[]{String.valueOf(id)});

        return modify != -1;
    }

    public boolean eliminaDieta (int id){
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete("DIETE", "ID = ?", new String[]{String.valueOf(id)});

        return delete != -1;
    }

    public boolean modificaNome(int id, String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NOME", nome);

        long modifica = db.update("DIETE", cv, "ID = ?", new String[]{String.valueOf(id)});

        return modifica != -1;
    }
}
