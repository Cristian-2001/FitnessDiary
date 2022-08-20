package com.example.applicazione.allenamento;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.applicazione.dieta.Dieta;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAllenamento extends SQLiteOpenHelper {

    public DataBaseAllenamento(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "allenamenti.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE ALLENAMENTI ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, NUM INTEGER, IDESERCIZI TEXT, SERIEESERCIZI TEXT, REPSESERCIZI TEXT, TRECESERCIZI TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean addOne (Allenamento allenamento){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put("ID",allenamento.getId());
        cv.put("NOME",allenamento.getNome());
        cv.put("NUM",allenamento.getNumElem());
        cv.put("IDESERCIZI", allenamento.IdToString());
        cv.put("SERIEESERCIZI", allenamento.SerieToString());
        cv.put("REPSESERCIZI", allenamento.RepsToString());
        cv.put("TRECESERCIZI", allenamento.TrecToString());


        long insert = db.insert("ALLENAMENTI", null, cv);

        return insert != -1;
    }

    public List<Allenamento> getAllAllenamenti(){

        SQLiteDatabase db = this.getReadableDatabase();

        List<Allenamento> returnList = new ArrayList<>();

        String queryString = "SELECT *" + " FROM ALLENAMENTI";

        Cursor cursor = db.rawQuery(queryString, null);

        Allenamento allenamento;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                int num = cursor.getInt(2);
                String idEsercizi = cursor.getString(3);
                String serieEsercizi = cursor.getString(4);
                String repsEsercizi = cursor.getString(5);
                String trecEsercizi = cursor.getString(6);



                allenamento = new Allenamento(id, nome, num);

                allenamento.setEserciziId(allenamento.ToArray(idEsercizi));
                allenamento.setEserciziSerie(allenamento.ToArray(serieEsercizi));
                allenamento.setEserciziReps(allenamento.ToArray(repsEsercizi));
                allenamento.setEserciziTrec(allenamento.ToArray(trecEsercizi));

                returnList.add(allenamento);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public Allenamento getAllenamentoById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM ALLENAMENTI WHERE ID = '" + id + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        Allenamento allenamento = null;

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(1);
            int num = cursor.getInt(2);
            String idEsercizi = cursor.getString(3);
            String serieEsercizi = cursor.getString(4);
            String repsEsercizi = cursor.getString(5);
            String trecEsercizi = cursor.getString(6);


            allenamento = new Allenamento(id, nome, num );

            allenamento.setEserciziId(allenamento.ToArray(idEsercizi));
            allenamento.setEserciziSerie(allenamento.ToArray(serieEsercizi));
            allenamento.setEserciziReps(allenamento.ToArray(repsEsercizi));
            allenamento.setEserciziTrec(allenamento.ToArray(trecEsercizi));


        }

        cursor.close();
        db.close();
        return allenamento;
    }

    public boolean modificaAllenamento (int id, Allenamento allenamento){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put("ID",allenamento.getId());
        cv.put("NOME",allenamento.getNome());
        cv.put("NUM",allenamento.getNumElem());
        cv.put("IDESERCIZI", allenamento.IdToString());
        cv.put("SERIEESERCIZI", allenamento.SerieToString());
        cv.put("REPSESERCIZI", allenamento.RepsToString());
        cv.put("TRECESERCIZI", allenamento.TrecToString());



        long modify = db.update("ALLENAMENTI", cv, "ID = ?", new String[]{String.valueOf(id)});

        return modify != -1;
    }

    public boolean eliminaAllenamento (int id){
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete("ALLENAMENTI", "ID = ?", new String[]{String.valueOf(id)});

        return delete != -1;
    }

    public boolean modificaNome(int id, String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NOME", nome);

        long modifica = db.update("ALLENAMENTI", cv, "ID = ?", new String[]{String.valueOf(id)});

        return modifica != -1;
    }
}
