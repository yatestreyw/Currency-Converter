package com.midlandstech.student.treywyates.currencyconverter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//Manages our database of Currencies
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "currencyDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CURRENCY = "currency";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the empty database with the id, name, and price fields
    public void onCreate(SQLiteDatabase db) {
        // build sql create statement
        String sqlCreate = "create table " + TABLE_CURRENCY + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + NAME;
        sqlCreate += " text, " + PRICE + " real )";

        db.execSQL(sqlCreate);
    }

    //Replaces the old instance with the newer database instance
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("drop table if exists " + TABLE_CURRENCY);
        // Re-create tables
        onCreate(db);
    }

    //Inserts the currency into the database
    public void insert(Currency currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_CURRENCY;
        sqlInsert += " values( null, '" + currency.getName();
        sqlInsert += "', '" + currency.getPrice() + "' )";

        db.execSQL(sqlInsert);
        db.close();
    }

    //Deletes the entry with the id given in the parameter
    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_CURRENCY;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    //Updates the entries name and price with the given id
    public void updateById(int id, String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_CURRENCY;
        sqlUpdate += " set " + NAME + " = '" + name + "', ";
        sqlUpdate += PRICE + " = '" + price + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL(sqlUpdate);
        db.close();
    }

    //Selects all entries in the database
    public ArrayList<Currency> selectAll() {
        String sqlQuery = "select * from " + TABLE_CURRENCY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Currency> currencys = new ArrayList<Currency>();
        while (cursor.moveToNext()) {
            Currency currentCurrency
                    = new Currency(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getDouble(2));
            currencys.add(currentCurrency);
        }
        db.close();
        return currencys;
    }

    //Selects and returns the certain entry with the given id
    public Currency selectById(int id) {
        String sqlQuery = "select * from " + TABLE_CURRENCY;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        Currency currency = null;
        if (cursor.moveToFirst())
            currency = new Currency(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getDouble(2));
        return currency;
    }
}
