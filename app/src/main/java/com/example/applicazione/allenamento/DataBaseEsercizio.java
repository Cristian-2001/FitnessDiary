package com.example.applicazione.allenamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.applicazione.dieta.Cibo;

import java.util.ArrayList;
import java.util.List;

public class DataBaseEsercizio extends SQLiteOpenHelper {

    public DataBaseEsercizio(@Nullable Context context) {
        super(context, "Esercizi.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE Esercizi (ID REAL," +
                "Nome VARCHAR(255)," +
                "Gruppo_muscolare VARCHAR(255)," +
                "Difficoltà VARCHAR(255)," +
                "Parte_del_corpo VARCHAR(255)," +
                "Tipologia VARCHAR(255)," +
                "Modalita VARCHAR(255))";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int nreVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean addOne(Esercizio esercizio) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ID", esercizio.getId());
        cv.put("Nome", esercizio.getNome());
        cv.put("Gruppo_muscolare", esercizio.getGruppoMuscolare());
        cv.put("Difficoltà", esercizio.getDifficolta());
        cv.put("Parte_del_corpo", esercizio.getParteDelCorpo());
        cv.put("Tipologia", esercizio.getTipologia());
        cv.put("Modalita", esercizio.getModalita());



        long insert = db.insert("Esercizi", null, cv);

        return insert != -1;

    }




    //ritorna l'esercizio con l'id dato

    public Esercizio getEsercizioById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM Esercizi  WHERE ID = '" + id + "'";

        Cursor cursor = db.rawQuery(queryString, null);

       Esercizio esercizio = null;

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(1);
            String gruppoMuscolare = cursor.getString(2);
            String difficolta = cursor.getString(3);
            String parteDelCorpo = cursor.getString(4);
            String tipologia = cursor.getString(5);
            String modalita = cursor.getString(6);

            esercizio = new Esercizio(id,nome,gruppoMuscolare,difficolta,parteDelCorpo,tipologia,modalita);
        }

        cursor.close();
        db.close();
        return esercizio;
    }

    //ritorna l'elenco di tutti gli esercizi

    public List<Esercizio> getAllEsercizi() {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM Esercizi";

        Cursor cursor = db.rawQuery(queryString, null);

        List<Esercizio> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String gruppoMuscolare = cursor.getString(2);
                String difficolta = cursor.getString(3);
                String parteDelCorpo = cursor.getString(4);
                String tipologia = cursor.getString(5);
                String modalita = cursor.getString(6);

                returnList.add(new Esercizio(id,nome,gruppoMuscolare,difficolta,parteDelCorpo,tipologia,modalita));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

}
