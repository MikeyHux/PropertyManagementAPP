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

public class PropertiesDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PropertiesDatabase";

    // Contacts table name
    private static final String TABLE_PROPERTIES = "properties";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_COMPANYNAME = "company_name";
    private static final String KEY_OWNERNAME = "ownername";
    private static final String KEY_SERVICES = "services";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_HOUSENUMBER = "house_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";


    private final ArrayList<Properties> properties_list = new ArrayList<Properties>();

    public PropertiesDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COMPANYNAME + " TEXT," + KEY_SERVICES + " TEXT," + KEY_OWNERNAME + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_HOUSENUMBER + " INTEGER," + KEY_EMAIL + " TEXT," + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_PROPERTIES_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Properties(Properties properties) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMPANYNAME, properties.getCompanyName()); // Contact Name
        values.put(KEY_SERVICES, properties.getServices());
        values.put(KEY_OWNERNAME, properties.getOwnerName()); // Contact Name
        values.put(KEY_ADDRESS, properties.getAddress());
        values.put(KEY_HOUSENUMBER, properties.getHouseNumber());
        values.put(KEY_EMAIL, properties.getEmail()); // Contact Name
        values.put(KEY_PHONE, properties.getPhone());

        // Inserting Row
        db.insert(TABLE_PROPERTIES, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    Properties Get_Properties(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROPERTIES, new String[] { KEY_ID,
                        KEY_COMPANYNAME, KEY_SERVICES, KEY_OWNERNAME, KEY_ADDRESS, KEY_HOUSENUMBER, KEY_EMAIL, KEY_PHONE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Properties properties = new Properties(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), (Integer.parseInt(cursor.getString(5))), cursor.getString(6), cursor.getString(7));
        // return contact
        cursor.close();
        db.close();

        return properties;
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
    public ArrayList<Properties> Get_Properties() {
        try {
            properties_list.clear();


            SQLiteDatabase db = this.getWritableDatabase();


            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES + " ORDER BY " + KEY_HOUSENUMBER + " ASC",null);


            if (cursor.moveToFirst()) {
                do {
                    Properties properties = new Properties();
                    properties.setID(Integer.parseInt(cursor.getString(0)));
                    properties.setCompanyName(cursor.getString(1));
                    properties.setServices(cursor.getString(2));
                    properties.setOwnerName(cursor.getString(3));
                    properties.setAddress(cursor.getString(4));
                    properties.setHouseNumber(Integer.parseInt(cursor.getString(5)));
                    properties.setEmail(cursor.getString(6));
                    properties.setPhone(cursor.getString(7));


                    properties_list.add(properties);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();


            return properties_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_companies", "" + e);
        }

        return properties_list;
    }

    // Updating single contact
    public int Update_Properties(Properties properties) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMPANYNAME, properties.getCompanyName());
        values.put(KEY_SERVICES, properties.getServices());
        values.put(KEY_OWNERNAME, properties.getOwnerName());
        values.put(KEY_ADDRESS, properties.getAddress());
        values.put(KEY_HOUSENUMBER, properties.getHouseNumber());
        values.put(KEY_EMAIL, properties.getEmail());
        values.put(KEY_PHONE, properties.getPhone());


        // updating row
        return db.update(TABLE_PROPERTIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(properties.getID()) });
    }

    // Deleting single contact
    public void Delete_Properties(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROPERTIES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int Get_Total_Properties() {
        String countQuery = "SELECT  * FROM " + TABLE_PROPERTIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



}
