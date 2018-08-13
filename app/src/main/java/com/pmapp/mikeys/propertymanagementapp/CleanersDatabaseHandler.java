package com.pmapp.mikeys.propertymanagementapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CleanersDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CleanersDatabase";

    // Contacts table name
    private static final String TABLE_CLEANERS = "cleaners";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CLEANERNAME = "cleaner_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_HOUSENUMBER = "house_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";


    private final ArrayList<Cleaners> cleaners_list = new ArrayList<Cleaners>();

    public CleanersDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLEANERS_TABLE = "CREATE TABLE " + TABLE_CLEANERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CLEANERNAME + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_HOUSENUMBER + " INTEGER," + KEY_EMAIL + " TEXT," + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_CLEANERS_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLEANERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Cleaners(Cleaners cleaners) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLEANERNAME, cleaners.getCleanerName()); // Contact Name
        values.put(KEY_ADDRESS, cleaners.getAddress());
        values.put(KEY_HOUSENUMBER, cleaners.getHouseNumber());
        values.put(KEY_EMAIL, cleaners.getEmail()); // Contact Name
        values.put(KEY_PHONE, cleaners.getPhone());

        // Inserting Row
        db.insert(TABLE_CLEANERS, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    Cleaners Get_Cleaners(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLEANERS, new String[] { KEY_ID,
                        KEY_CLEANERNAME, KEY_ADDRESS, KEY_HOUSENUMBER, KEY_EMAIL, KEY_PHONE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Cleaners cleaners = new Cleaners(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), (Integer.parseInt(cursor.getString(3))), cursor.getString(4), cursor.getString(5));
        // return contact
        cursor.close();
        db.close();

        return cleaners;
    }

/*
    public List getAllProperties() {
        List propertiesList = new ArrayList();
        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES + " ORDER BY " + KEY_ADDRESS + " ASC", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Properties properties = new Properties();
                properties.setID(Integer.parseInt(cursor.getString(0)));
                properties.setCompanyName(cursor.getString(1));
                properties.setServices(cursor.getString(2));
                properties.setOwnerName(cursor.getString(3));
                properties.setAddress(cursor.getString(4));
                properties.setEmail(cursor.getString(5));
                properties.setPhone(cursor.getString(6));



                // Adding country to list
                propertiesList.add(properties);
            } while (cursor.moveToNext());
        }

        // return country list
        return propertiesList;
    }

    //Get all companies from List
    /*public List<String> getAllProperties(){

        List<String> properties = new ArrayList<String>();

        // Select All Query
        // String selectQuery = "SELECT * FROM " + TABLE_COMPANIES +" ORDER BY " + KEY_COMPANYNAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        //Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES +" ORDER BY " + KEY_ADDRESS + " DESC",null);
        Cursor cursor = db.rawQuery("SELECT " + KEY_COMPANYNAME + KEY_SERVICES + KEY_OWNERNAME + KEY_ADDRESS + KEY_EMAIL + KEY_PHONE + " FROM " + TABLE_PROPERTIES +" ORDER BY " + KEY_ADDRESS + " DESC",null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               // properties.add((cursor.getString(1)) + " - " + (cursor.getString(2)) );

                properties.add(cursor.getString(1));
                properties.add(cursor.getString(2));
                properties.add(cursor.getString(3));
                properties.add(cursor.getString(4));
                properties.add(cursor.getString(5));
                properties.add(cursor.getString(6));



            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return properties;
    }


**/

    // Getting All Contacts
    public ArrayList<Cleaners> Get_Cleaners() {
        try {
            cleaners_list.clear();


            SQLiteDatabase db = this.getWritableDatabase();


            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLEANERS + " ORDER BY " + KEY_CLEANERNAME + " ASC",null);


            if (cursor.moveToFirst()) {
                do {
                    Cleaners cleaners = new Cleaners();
                    cleaners.setID(Integer.parseInt(cursor.getString(0)));
                    cleaners.setCleanerName(cursor.getString(1));
                    cleaners.setAddress(cursor.getString(2));
                    cleaners.setHouseNumber(Integer.parseInt(cursor.getString(3)));
                    cleaners.setEmail(cursor.getString(4));
                    cleaners.setPhone(cursor.getString(5));


                    cleaners_list.add(cleaners);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();


            return cleaners_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_companies", "" + e);
        }

        return cleaners_list;
    }

    // Updating single contact
    public int Update_Cleaners(Cleaners cleaners) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLEANERNAME, cleaners.getCleanerName());
        values.put(KEY_ADDRESS, cleaners.getAddress());
        values.put(KEY_HOUSENUMBER, cleaners.getHouseNumber());
        values.put(KEY_EMAIL, cleaners.getEmail());
        values.put(KEY_PHONE, cleaners.getPhone());


        // updating row
        return db.update(TABLE_CLEANERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(cleaners.getID()) });
    }

    // Deleting single contact
    public void Delete_Cleaners(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLEANERS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int Get_Total_Cleaners() {
        String countQuery = "SELECT  * FROM " + TABLE_CLEANERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



}

