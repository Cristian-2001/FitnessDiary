package com.example.applicazione.allenamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseEsercizio extends SQLiteOpenHelper {
    private Context myContext;

    public DataBaseEsercizio(@Nullable Context context) {
        super(context, "Esercizii.db", null, 1);
        myContext = context;
        this.getReadableDatabase();
        try {
            copyDataBase("Esercizii.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*String createTableStatement = "CREATE TABLE Esercizi (ID REAL," +
                "Nome VARCHAR(255)," +
                "Gruppo_muscolare VARCHAR(255)," +
                "Difficoltà VARCHAR(255)," +
                "Parte_del_corpo VARCHAR(255)," +
                "Tipologia VARCHAR(255)," +
                "Modalita VARCHAR(255))";

        db.execSQL(createTableStatement);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int nreVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase(String dbname) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(dbname);
        // Path to the just created empty db
        File outFileName = myContext.getDatabasePath(dbname);
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
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

            esercizio = new Esercizio(id, nome, gruppoMuscolare, difficolta, parteDelCorpo, tipologia, modalita);
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

                returnList.add(new Esercizio(id, nome, gruppoMuscolare, difficolta, parteDelCorpo, tipologia, modalita));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public List<Esercizio> getEserciziFiltri(String nome, String gruppoMusc, String diff, String parteCorpo,
                                             String tipologia, String modalita) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (nome.equals("")) {
            nome = "%";
        }

        if (gruppoMusc.equals("Qualsiasi")) {
            gruppoMusc = "%";
        }

        if (diff.equals("Qualsiasi")) {
            diff = "%";
        }

        if (parteCorpo.equals("Qualsiasi")) {
            parteCorpo = "%";
        }

        if (tipologia.equals("Qualsiasi")) {
            tipologia = "%";
        }

        if (modalita.equals("Qualsiasi")) {
            modalita = "%";
        }

        String queryString = "SELECT * FROM Esercizi WHERE Nome like '%" + nome
                + "%' AND Gruppo_muscolare LIKE '" + gruppoMusc
                + "' AND Difficoltà LIKE '" + diff
                + "' AND Parte_del_corpo LIKE '" + parteCorpo
                + "' AND Tipologia LIKE '" + tipologia
                + "' AND Modalita LIKE '" + modalita + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        List<Esercizio> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome2 = cursor.getString(1);
                String gruppoMuscolare2 = cursor.getString(2);
                String difficolta2 = cursor.getString(3);
                String parteDelCorpo2 = cursor.getString(4);
                String tipologia2 = cursor.getString(5);
                String modalita2 = cursor.getString(6);

                returnList.add(new Esercizio(id, nome2, gruppoMuscolare2, difficolta2, parteDelCorpo2, tipologia2, modalita2));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }
}
