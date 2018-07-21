package com.pmapp.mikeys.propertymanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class CompaniesDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CompaniesDatabase";

    // Contacts table name
    private static final String TABLE_COMPANIES = "companies";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_COMPANYNAME = "company_name";

    private final ArrayList<Companies> companies_list = new ArrayList<Companies>();

    public CompaniesDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COMPANIES_TABLE = "CREATE TABLE " + TABLE_COMPANIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COMPANYNAME + " TEXT," + " TEXT" + ")";
        db.execSQL(CREATE_COMPANIES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANIES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Companies(Companies companies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMPANYNAME, companies.getCompanyName()); // Contact Name

        // Inserting Row
        db.insert(TABLE_COMPANIES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
        Companies Get_Companies(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPANIES, new String[] { KEY_ID,
                        KEY_COMPANYNAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Companies companies = new Companies(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        cursor.close();
        db.close();

        return companies;
    }

    // Getting All Contacts
    public ArrayList<Companies> Get_Companies() {
        try {
            companies_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_COMPANIES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Companies companies = new Companies();
                    companies.setID(Integer.parseInt(cursor.getString(0)));
                    companies.setCompanyName(cursor.getString(1));

                    // Adding contact to list
                    companies_list.add(companies);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return companies_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_companies", "" + e);
        }

        return companies_list;
    }

    // Updating single contact
    public int Update_Companies(Companies companies) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMPANYNAME, companies.getCompanyName());


        // updating row
        return db.update(TABLE_COMPANIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(companies.getID()) });
    }

    // Deleting single contact
    public void Delete_Companies(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPANIES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int Get_Total_Companies() {
        String countQuery = "SELECT  * FROM " + TABLE_COMPANIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


}
