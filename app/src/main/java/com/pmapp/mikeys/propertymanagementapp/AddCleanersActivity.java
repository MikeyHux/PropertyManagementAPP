package com.pmapp.mikeys.propertymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddCleanersActivity extends AppCompatActivity  {

    String Command;
    String CleanerName;
    String Email;
    String Phone;
    String CleanerExist;
    String Address;
    Boolean CleanerDoesExist = false;
    Button btnAddCleaner;
    Button btnCancel;
    TextView txtViewCleanerName;
    EditText edtCleanerName;
    TextView txtViewAddress;
    EditText edtAddress;
    TextView txtViewEmail;
    EditText edtEmail;
    TextView txtViewPhone;
    EditText edtPhone;
    int CLEANER_ID;
    int HouseNumber;

    CleanersDatabaseHandler CleandbHandler = new CleanersDatabaseHandler(AddCleanersActivity.this);




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcleaners);

        edtCleanerName = (EditText)findViewById(R.id.edtCleanerName);
        txtViewAddress = (TextView) findViewById(R.id.txtViewAddress);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        txtViewEmail = (TextView) findViewById(R.id.txtViewEmail);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        txtViewPhone = (TextView) findViewById(R.id.txtViewPhone);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        btnAddCleaner = (Button) findViewById(R.id.btnAddCleaner);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //btnAddCleaner.setVisibility(View.INVISIBLE);
        //btnCancel.setVisibility(View.INVISIBLE);

        Command = getIntent().getStringExtra("Command");
        //Toast.makeText(this, "Command: " + Command,Toast.LENGTH_LONG).show();

        try {

            Command = getIntent().getStringExtra("Command");

            Toast.makeText(this, "called from!: " + Command, Toast.LENGTH_LONG).show();

            if(Command.equals("EDIT")){

                //String Company_ID = getIntent().getStringExtra("COMPANY_ID");
                CLEANER_ID = Integer.parseInt(getIntent().getStringExtra("CLEANER_ID"));
                Cleaners cleaners = CleandbHandler.Get_Cleaners(CLEANER_ID);
                CleanerName = cleaners.getCleanerName().trim();
                Address = cleaners.getAddress();
                HouseNumber = cleaners.getHouseNumber();
                Email = cleaners.getEmail();
                Phone = cleaners.getPhone();

                edtCleanerName.setText(CleanerName);
                edtAddress.setText(Address);
                edtEmail.setText(Email);
                edtPhone.setText(Phone);

                //Substring
                String House_Number = Address.substring(0,Address.indexOf(' '));

                //Convert into integer
                HouseNumber = Integer.parseInt(House_Number.toString());


                btnAddCleaner.setText("SAVE");


            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);

        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCleanersActivity.this, CleanersDatabaseActivity.class));

                Toast.makeText(AddCleanersActivity.this, "User Cancels!", Toast.LENGTH_LONG).show();

            }
        });

        btnAddCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Command.equals("ADD") && (edtCleanerName.length() !=0) && (edtAddress.length() !=0) && (edtEmail.length() !=0) && (edtPhone.length() !=0)  ) {

                    Toast.makeText(AddCleanersActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    CleanerName = edtCleanerName.getText().toString().trim();
                    Address = edtAddress.getText().toString().trim();
                    Email = edtEmail.getText().toString().trim();
                    Phone = edtPhone.getText().toString().trim();

                    //Substring
                    String House_Number = Address.substring(0,Address.indexOf(' '));

                    //Convert into integer
                    HouseNumber = Integer.parseInt(House_Number.toString());


                    CheckDatabase();

                    CheckCleaner();

                    Toast.makeText(AddCleanersActivity.this, "Cleaner??!" + CleanerDoesExist, Toast.LENGTH_LONG).show();


                    if(CleanerDoesExist.equals(false)){


                        CleandbHandler.Add_Cleaners(new Cleaners(CleanerName, Address, HouseNumber, Email, Phone ));

                        Toast.makeText(AddCleanersActivity.this, "Cleaner ADDED!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(AddCleanersActivity.this, CleanersDatabaseActivity.class));


                    } else {

                        Toast.makeText(AddCleanersActivity.this, "Cleaner Does Exist!", Toast.LENGTH_LONG).show();
                        edtCleanerName.setText("");

                    }



                } else {

                    if(Command.equals("ADD") && ((edtCleanerName.length() ==0) || (edtAddress.length() ==0) || (edtEmail.length() ==0) || (edtPhone.length() ==0) ) ){


                        Toast.makeText(AddCleanersActivity.this, "Please Make Sure All Fields are not EMPTY!", Toast.LENGTH_LONG).show();


                    }


                }


                //EDIT FUNCTION

                if(Command.equals("EDIT") &&  (edtCleanerName.length() !=0) && (edtAddress.length() !=0) && (edtEmail.length() !=0) && (edtPhone.length() !=0)  ) {

                    Toast.makeText(AddCleanersActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    CleanerName = edtCleanerName.getText().toString().trim();
                    Address = edtAddress.getText().toString().trim();
                    Email = edtEmail.getText().toString().trim();
                    Phone = edtPhone.getText().toString().trim();

                    //Substring
                    String House_Number = Address.substring(0,Address.indexOf(' '));

                    //Convert into integer
                    HouseNumber = Integer.parseInt(House_Number.toString());


                    CleandbHandler.Update_Cleaners(new Cleaners(CLEANER_ID, CleanerName, Address, HouseNumber, Email, Phone));

                    startActivity(new Intent(AddCleanersActivity.this, CleanersDatabaseActivity.class));

                } else {

                    Toast.makeText(AddCleanersActivity.this, "PLEASE MAKE SURE ALL FIELDS ARE NOT EMPTY!", Toast.LENGTH_LONG).show();

                }


            }



        });



    }

    public void CheckDatabase(){

        File dbtest = new File("/data/data/com.pmapp.mikeys.propertymanagementapp/databases/PropertiesDatabase");

        if(dbtest.exists())
        {
            Toast.makeText(this, "PropertiesDatabase Does Exist: ",Toast.LENGTH_LONG).show();
        }
        else
        {
            // what to do if it doesn't exist
            Toast.makeText(this, "PropertiesDatabase Does Not Exist - Will Create One: ",Toast.LENGTH_LONG).show();
        }
    }

    public void CheckCleaner(){


        CleanersDatabaseHandler db = new CleanersDatabaseHandler(AddCleanersActivity.this);
        ArrayList<Cleaners> cleaners = db.Get_Cleaners();

        for (int i = 0; i < cleaners.size(); i++) {

            int CompID = cleaners.get(i).getID();
            CleanerExist = cleaners.get(i).getCleanerName().trim();

            Toast.makeText(this, "Cleaner: " + CleanerExist,Toast.LENGTH_LONG).show();

            if(CleanerName.equals(CleanerExist)){

                CleanerDoesExist = true;

            }



        };

    }



}

