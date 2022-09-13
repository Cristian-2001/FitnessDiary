package com.example.applicazione.dieta;

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

public class DataBaseCibo extends SQLiteOpenHelper {

    private static final String TABLE_CIBI = "CIBI";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NOME = "NOME";
    private static final String COLUMN_CATEGORIA = "CATEGORIA";
    private static final String COLUMN_ENERGIA = "ENERGIA";
    private static final String COLUMN_LIPIDI = "LIPIDI";
    private static final String COLUMN_ACIDI_GRASSI_SATURI = "ACIDI_GRASSI_SATURI";
    private static final String COLUMN_COLESTEROLO = "COLESTEROLO";
    private static final String COLUMN_CARBOIDRATI = "CARBOIDRATI";
    private static final String COLUMN_ZUCCHERI = "ZUCCHERI";
    private static final String COLUMN_FIBRE = "FIBRE";
    private static final String COLUMN_PROTEINE = "PROTEINE";
    private static final String COLUMN_SALE = "SALE";

    private Context myContext;

    public DataBaseCibo(@Nullable Context context) {
        super(context, "ValoriNutrizionali.db", null, 1);
        myContext = context;
        try {
            copyDataBase("ValoriNutrizionali.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //chiamato la prima volta che viene fatto un accesso al database quindi deve contenere codice per creare un nuovo db
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //chiamato quando cambia la versione del db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /**
     * Copia il tuo database locale dalla cartella locale assets nel database vuoto appena creato nella cartella di sistema
     * dalla quale puo essere gestito. Questo è effettuato con un trasferimento di byte
     **/

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

    //i miei metodi possono essere messi anche qui


    public boolean addOne(Cibo cibo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, cibo.getId());
        cv.put(COLUMN_NOME, cibo.getNome());
        cv.put(COLUMN_CATEGORIA, cibo.getCategoria());
        cv.put(COLUMN_ENERGIA, cibo.getEnergia());
        cv.put(COLUMN_LIPIDI, cibo.getLipidi());
        cv.put(COLUMN_ACIDI_GRASSI_SATURI, cibo.getAcidigrassi());
        cv.put(COLUMN_COLESTEROLO, cibo.getColesterolo());
        cv.put(COLUMN_CARBOIDRATI, cibo.getCarboidrati());
        cv.put(COLUMN_ZUCCHERI, cibo.getZuccheri());
        cv.put(COLUMN_FIBRE, cibo.getFibre());
        cv.put(COLUMN_PROTEINE, cibo.getProteine());
        cv.put(COLUMN_SALE, cibo.getSale());


        long insert = db.insert(TABLE_CIBI, null, cv);

        return insert != -1;

    }


    //ritorna il Cibo con l'id dato
    public Cibo getCiboById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM " + TABLE_CIBI + " WHERE " + COLUMN_ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        Cibo cibo = null;

        if (cursor.moveToFirst()) {
            String nome = cursor.getString(1);
            String categoria = cursor.getString(2);
            Double energia = cursor.getDouble(3);
            Double lipidi = cursor.getDouble(4);
            Double acidigrassi = cursor.getDouble(5);
            Double colesterolo = cursor.getDouble(6);
            Double carboidrati = cursor.getDouble(7);
            Double zuccheri = cursor.getDouble(8);
            Double fibre = cursor.getDouble(9);
            Double proteine = cursor.getDouble(10);
            Double sale = cursor.getDouble(11);
            cibo = new Cibo(id, nome, categoria, energia, lipidi, acidigrassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale);
        }

        cursor.close();
        db.close();
        return cibo;
    }

    //ritorna l'elenco di tutti i cibi
    public List<Cibo> getAllCibi() {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM " + TABLE_CIBI;

        Cursor cursor = db.rawQuery(queryString, null);

        List<Cibo> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String categoria = cursor.getString(2);
                Double energia = cursor.getDouble(3);
                Double lipidi = cursor.getDouble(4);
                Double acidigrassi = cursor.getDouble(5);
                Double colesterolo = cursor.getDouble(6);
                Double carboidrati = cursor.getDouble(7);
                Double zuccheri = cursor.getDouble(8);
                Double fibre = cursor.getDouble(9);
                Double proteine = cursor.getDouble(10);
                Double sale = cursor.getDouble(11);
                returnList.add(new Cibo(id, nome, categoria, energia, lipidi, acidigrassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    //ritorna l'elenco di cibi della categoria data
    public List<Cibo> getCibiByCatNome(String cat, String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        if (cat.equals("Tutte")) {
            cat = "%";
        }

        if (name.equals("")) {
            name = "%";
        }

        String queryString = "SELECT *" + " FROM " + TABLE_CIBI + " WHERE " + COLUMN_CATEGORIA + " like '" + cat + "' AND "
                + COLUMN_NOME + " like '%" + name + "%'";

        Cursor cursor = db.rawQuery(queryString, null);

        List<Cibo> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String categoria = cursor.getString(2);
                Double energia = cursor.getDouble(3);
                Double lipidi = cursor.getDouble(4);
                Double acidigrassi = cursor.getDouble(5);
                Double colesterolo = cursor.getDouble(6);
                Double carboidrati = cursor.getDouble(7);
                Double zuccheri = cursor.getDouble(8);
                Double fibre = cursor.getDouble(9);
                Double proteine = cursor.getDouble(10);
                Double sale = cursor.getDouble(11);
                returnList.add(new Cibo(id, nome, categoria, energia, lipidi, acidigrassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }
}
