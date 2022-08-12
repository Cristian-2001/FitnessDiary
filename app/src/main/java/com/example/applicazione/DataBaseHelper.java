package com.example.applicazione;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


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

    public DataBaseHelper(@Nullable Context context) {
        super(context, "ValoriNutrizionali.db", null, 3);
    }

    //chiamato la prima volta che viene fatto un accesso al database quindi deve contenere codice per creare un nuovo db
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*String createTableStatement = "CREATE TABLE " + TABLE_CIBI + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOME + " TEXT, " + COLUMN_CATEGORIA + " TEXT, " + COLUMN_ENERGIA + " INTEGER, " + COLUMN_LIPIDI + " DOUBLE(3,1), " + COLUMN_ACIDI_GRASSI_SATURI + " DOUBLE(3,1), " + COLUMN_COLESTEROLO + " INTEGER, " + COLUMN_CARBOIDRATI + " DOUBLE(3,1), " + COLUMN_ZUCCHERI + " DOUBLE(3,1), " + COLUMN_FIBRE + " DOUBLE(3,1), " + COLUMN_PROTEINE + " DOUBLE(3,1), " + COLUMN_SALE + " DOUBLE(2,1))";

        String test = "CREATE TABLE \"Cibi\" (\"ID\" REAL, \"NOME\" VARCHAR(255), \"CATEGORIA\" VARCHAR(255), \"ENERGIA\" REAL, \"LIPIDI\" VARCHAR(255), \"ACIDI_GRASSI_SATURI\" REAL, \"COLESTEROLO\" REAL, \"CARBOIDRATI\" REAL, \"ZUCCHERI\" REAL, \"FIBRE\" REAL, \"PROTEINE\" REAL, \"SALE\" REAL)";
        db.execSQL(createTableStatement);*/

    }

    //chiamato quando cambia la versione del db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
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

    public List<Double> getCarbo(String nome) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT " + COLUMN_CARBOIDRATI + " FROM " + TABLE_CIBI + " WHERE " + COLUMN_NOME + " = '" + nome + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        List<Double> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                double carbo = cursor.getDouble(0);
                returnList.add(carbo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;


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
    public ArrayList<Cibo> getAllCibi() {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM " + TABLE_CIBI;

        Cursor cursor = db.rawQuery(queryString, null);

        ArrayList<Cibo> returnList = new ArrayList<>();

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
    public ArrayList<Cibo> getCibiByCat(String cat) {

        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT *" + " FROM " + TABLE_CIBI + " WHERE " + COLUMN_CATEGORIA + " = '" + cat + "'";

        Cursor cursor = db.rawQuery(queryString, null);

        ArrayList<Cibo> returnList = new ArrayList<>();

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
