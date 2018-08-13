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


public class AddPropertiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String CompanyNameAndService;
    String Command;
    String Service;
    String CompanyName;
    String OwnerName;
    String Email;
    String Phone;
    String AddressExist;
    String Address;
    Boolean AddressDoesExist = false;
    Spinner SpinnerAddProperties;
    Button btnAddProperty;
    Button btnCancel;
    TextView txtViewOwnerName;
    EditText edtOwnerName;
    TextView txtViewAddress;
    EditText edtAddress;
    TextView txtViewOwnerEmail;
    EditText edtEmail;
    TextView txtViewPhone;
    EditText edtPhone;
    int GetCount;
    int PROPERTY_ID;
    int HouseNumber;

    PropertiesDatabaseHandler PropdbHandler = new PropertiesDatabaseHandler(AddPropertiesActivity.this);

    String spinnerSelection;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproperties);

        // Spinner element
        SpinnerAddProperties = (Spinner) findViewById(R.id.SpinnerAddProperties);
        txtViewOwnerName = (TextView) findViewById(R.id.txtViewOwnerName);
        edtOwnerName = (EditText)findViewById(R.id.edtOwnerName);
        txtViewAddress = (TextView) findViewById(R.id.txtViewAddress);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        txtViewOwnerEmail = (TextView) findViewById(R.id.txtViewOwnerEmail);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        txtViewPhone = (TextView) findViewById(R.id.txtViewPhone);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        btnAddProperty = (Button) findViewById(R.id.btnAddProperty);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        txtViewOwnerName.setVisibility(View.INVISIBLE);
        edtOwnerName.setVisibility(View.INVISIBLE);
        txtViewAddress.setVisibility(View.INVISIBLE);
        edtAddress.setVisibility(View.INVISIBLE);
        txtViewOwnerEmail.setVisibility(View.INVISIBLE);
        edtEmail.setVisibility(View.INVISIBLE);
        txtViewPhone.setVisibility(View.INVISIBLE);
        edtPhone.setVisibility(View.INVISIBLE);
        btnAddProperty.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);


        // Spinner click listener
        SpinnerAddProperties.setOnItemSelectedListener(this);


        Command = getIntent().getStringExtra("Command");
        //Toast.makeText(this, "Command: " + Command,Toast.LENGTH_LONG).show();

        try {

            Command = getIntent().getStringExtra("Command");

            Toast.makeText(this, "called from!: " + Command, Toast.LENGTH_LONG).show();

            if(Command.equals("EDIT")){

                //String Company_ID = getIntent().getStringExtra("COMPANY_ID");
                PROPERTY_ID = Integer.parseInt(getIntent().getStringExtra("PROPERTY_ID"));
                Properties properties = PropdbHandler.Get_Properties(PROPERTY_ID);
                CompanyName = properties.getCompanyName().trim();
                Service = properties.getServices().trim();
                CompanyNameAndService = CompanyName + " - " + Service;
                OwnerName = properties.getOwnerName();
                Address = properties.getAddress();
                HouseNumber = properties.getHouseNumber();
                Email = properties.getEmail();
                Phone = properties.getPhone();

                btnAddProperty.setText("SAVE");


            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);

        }



       loadSpinnerData();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPropertiesActivity.this, PropertiesDatabaseActivity.class));

                Toast.makeText(AddPropertiesActivity.this, "User Cancels!", Toast.LENGTH_LONG).show();

            }
        });

        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Command.equals("ADD") && Service.equals("Management and Cleaning") && (edtOwnerName.length() !=0) && (edtAddress.length() !=0) && (edtEmail.length() !=0) && (edtPhone.length() !=0)  ) {

                    Toast.makeText(AddPropertiesActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    OwnerName = edtOwnerName.getText().toString().trim();
                    Address = edtAddress.getText().toString().trim();
                    Email = edtEmail.getText().toString().trim();
                    Phone = edtPhone.getText().toString().trim();

                    //Substring
                    String House_Number = Address.substring(0,Address.indexOf(' '));

                    //Convert into integer
                    HouseNumber = Integer.parseInt(House_Number.toString());


                    CheckDatabase();

                    CheckAddress();

                    Toast.makeText(AddPropertiesActivity.this, "Address??!" + AddressDoesExist, Toast.LENGTH_LONG).show();


                    if(AddressDoesExist.equals(false)){


                        PropdbHandler.Add_Properties(new Properties(CompanyName, Service, OwnerName, Address, HouseNumber, Email, Phone ));

                        Toast.makeText(AddPropertiesActivity.this, "Address ADDED!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(AddPropertiesActivity.this, PropertiesDatabaseActivity.class));


                    } else {

                        Toast.makeText(AddPropertiesActivity.this, "Address Does Exist!", Toast.LENGTH_LONG).show();
                        edtAddress.setText("");

                    }



                } else {

                    if(Command.equals("ADD") && Service.equals("Management and Cleaning") && ((edtOwnerName.length() ==0) || (edtAddress.length() ==0) || (edtEmail.length() ==0) || (edtPhone.length() ==0) ) ){


                        Toast.makeText(AddPropertiesActivity.this, "Please Make Sure All Fields are not EMPTY!", Toast.LENGTH_LONG).show();


                    }


                }


                if(Command.equals("ADD") && Service.equals("Only Cleaning") && (edtAddress.length() !=0)) {

                    Toast.makeText(AddPropertiesActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    Address = edtAddress.getText().toString().trim();

                    //Substring
                    String House_Number = Address.substring(0,Address.indexOf(' '));

                    //Convert into integer
                    HouseNumber = Integer.parseInt(House_Number.toString());

                    //Email = edtEmail.getText().toString().trim();
                    //Phone = edtPhone.getText().toString().trim();

                    CheckAddress();




                    if(AddressDoesExist.equals(false)){


                        PropdbHandler.Add_Properties(new Properties(CompanyName, Service, OwnerName, Address, HouseNumber, Email, Phone ));

                        startActivity(new Intent(AddPropertiesActivity.this, PropertiesDatabaseActivity.class));


                    } else {

                        Toast.makeText(AddPropertiesActivity.this, "Address Does Exist!", Toast.LENGTH_LONG).show();
                        edtAddress.setText("");

                    }


                } else {

                    if(Command.equals("ADD") && Service.equals("Only Cleaning") && ((edtAddress.length() ==0) || (edtEmail.length() ==0) || (edtPhone.length() ==0) ) ){


                        Toast.makeText(AddPropertiesActivity.this, "Please Make Sure All Fields are not EMPTY!", Toast.LENGTH_LONG).show();


                    }


                }

                //EDIT FUNCTION

                if(Command.equals("EDIT") && Service.equals("Management and Cleaning") && (edtOwnerName.length() !=0) && (edtAddress.length() !=0) && (edtEmail.length() !=0) && (edtPhone.length() !=0)  ) {

                    Toast.makeText(AddPropertiesActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    OwnerName = edtOwnerName.getText().toString().trim();
                    Address = edtAddress.getText().toString().trim();
                    Email = edtEmail.getText().toString().trim();
                    Phone = edtPhone.getText().toString().trim();

                    //Substring
                    String House_Number = Address.substring(0,Address.indexOf(' '));

                    //Convert into integer
                    HouseNumber = Integer.parseInt(House_Number.toString());


                    PropdbHandler.Update_Properties(new Properties(PROPERTY_ID, CompanyName, Service, OwnerName, Address, HouseNumber, Email, Phone));

                    startActivity(new Intent(AddPropertiesActivity.this, PropertiesDatabaseActivity.class));

                } else {

                    Toast.makeText(AddPropertiesActivity.this, "PLEASE MAKE SURE ALL FIELDS ARE NOT EMPTY!", Toast.LENGTH_LONG).show();

                }

                if(Command.equals("EDIT") && Service.equals("Only Cleaning") && (edtAddress.length() !=0)) {

                    Toast.makeText(AddPropertiesActivity.this, "Going to ADD!", Toast.LENGTH_LONG).show();

                    Address = edtAddress.getText().toString().trim();

                    PropdbHandler.Update_Properties(new Properties(PROPERTY_ID, CompanyName, Service, OwnerName, Address, HouseNumber, Email, Phone));

                    startActivity(new Intent(AddPropertiesActivity.this, PropertiesDatabaseActivity.class));

                } else {

                    Toast.makeText(AddPropertiesActivity.this, "PLEASE MAKE SURE ADDRESS FIELD IS NOT EMPTY!", Toast.LENGTH_LONG).show();

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

    public void CheckAddress(){


        PropertiesDatabaseHandler db = new PropertiesDatabaseHandler(AddPropertiesActivity.this);
        ArrayList<Properties> properties_array_from_db = db.Get_Properties();

        for (int i = 0; i < properties_array_from_db.size(); i++) {

            int CompID = properties_array_from_db.get(i).getID();
            AddressExist = properties_array_from_db.get(i).getAddress().trim();

            Toast.makeText(this, "Address: " + AddressExist,Toast.LENGTH_LONG).show();

            if(Address.equals(AddressExist)){

                AddressDoesExist = true;

            }



        };

    }



    public void loadSpinnerData() {

        CompaniesDatabaseHandler CompdbHandler = new CompaniesDatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> companies = CompdbHandler.getAllCompanies();

        // Creating adapter for spinner
       ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, companies);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.add("Please Choose a Company:");

        // attaching data adapter to spinner
        SpinnerAddProperties.setAdapter(dataAdapter);

            for (int i = 0; i < dataAdapter.getCount(); i++) {
                Object obj = dataAdapter.getItem(i);

                if(Command.equals("ADD")) {
                    if (obj.equals("Please Choose a Company:")) {

                        //Toast.makeText(this, "count: " + i,Toast.LENGTH_LONG).show();
                        SpinnerAddProperties.setSelection(i);

                    }
                }

                if(Command.equals("EDIT")){

                    if (obj.equals(CompanyNameAndService)) {

                        //Toast.makeText(this, "count: " + i,Toast.LENGTH_LONG).show();
                        SpinnerAddProperties.setSelection(i);

                    }

                }

            }
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinnerSelection = parent.getItemAtPosition(position).toString();

        if(Command.equals("ADD") && spinnerSelection.contains("Management and Cleaning")) {

            Toast.makeText(parent.getContext(), "ADD and management and cleaning: " + spinnerSelection, Toast.LENGTH_LONG).show();

            txtViewOwnerName.setVisibility(View.VISIBLE);
            edtOwnerName.setVisibility(View.VISIBLE);
            txtViewAddress.setVisibility(View.VISIBLE);
            edtAddress.setVisibility(View.VISIBLE);
            txtViewOwnerEmail.setVisibility(View.VISIBLE);
            edtEmail.setVisibility(View.VISIBLE);
            txtViewPhone.setVisibility(View.VISIBLE);
            edtPhone.setVisibility(View.VISIBLE);
            btnAddProperty.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);


            CompanyName = spinnerSelection.replace("- Management and Cleaning", "").trim();
            Service = "Management and Cleaning";

            //Toast.makeText(this, "Company Name: " + CompanyName,Toast.LENGTH_LONG).show();


        } else {

            if(Command.equals("ADD") && spinnerSelection.contains("Only Cleaning")) {

                txtViewOwnerName.setVisibility(View.INVISIBLE);
                edtOwnerName.setVisibility(View.INVISIBLE);
                txtViewAddress.setVisibility(View.VISIBLE);
                edtAddress.setVisibility(View.VISIBLE);
                txtViewOwnerEmail.setVisibility(View.INVISIBLE);
                edtEmail.setVisibility(View.INVISIBLE);
                txtViewPhone.setVisibility(View.INVISIBLE);
                edtPhone.setVisibility(View.INVISIBLE);
                btnAddProperty.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                CompanyName = spinnerSelection.replace("- Only Cleaning", "").trim();
                edtOwnerName.equals("Not Applicable");
                edtEmail.equals("Not Applicable");
                edtPhone.equals("Not Applicable");
                Service = "Only Cleaning";

                //Toast.makeText(this, "Company Name: " + CompanyName,Toast.LENGTH_LONG).show();

            }



        }

        if(Command.equals("EDIT") && spinnerSelection.contains("Management and Cleaning")) {

            Toast.makeText(parent.getContext(), "ADD and management and cleaning: " + spinnerSelection, Toast.LENGTH_LONG).show();

            txtViewOwnerName.setVisibility(View.VISIBLE);
            edtOwnerName.setVisibility(View.VISIBLE);
            txtViewAddress.setVisibility(View.VISIBLE);
            edtAddress.setVisibility(View.VISIBLE);
            txtViewOwnerEmail.setVisibility(View.VISIBLE);
            edtEmail.setVisibility(View.VISIBLE);
            txtViewPhone.setVisibility(View.VISIBLE);
            edtPhone.setVisibility(View.VISIBLE);
            btnAddProperty.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);


            CompanyName = spinnerSelection.replace("- Management and Cleaning", "").trim();
            Service = "Management and Cleaning";

            edtOwnerName.setText(OwnerName);
            edtAddress.setText(Address);
            edtEmail.setText(Email);
            edtPhone.setText(Phone);

            //Substring
            String House_Number = Address.substring(0,Address.indexOf(' '));

            //Convert into integer
            HouseNumber = Integer.parseInt(House_Number.toString());



            //Toast.makeText(this, "Company Name: " + CompanyName,Toast.LENGTH_LONG).show();


        } else {

            if(Command.equals("EDIT") && spinnerSelection.contains("Only Cleaning")) {

                txtViewOwnerName.setVisibility(View.INVISIBLE);
                edtOwnerName.setVisibility(View.INVISIBLE);
                txtViewAddress.setVisibility(View.VISIBLE);
                edtAddress.setVisibility(View.VISIBLE);
                txtViewOwnerEmail.setVisibility(View.INVISIBLE);
                edtEmail.setVisibility(View.INVISIBLE);
                txtViewPhone.setVisibility(View.INVISIBLE);
                edtPhone.setVisibility(View.INVISIBLE);
                btnAddProperty.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                CompanyName = spinnerSelection.replace("- Only Cleaning", "").trim();
                edtOwnerName.equals("Not Applicable");
                edtEmail.equals("Not Applicable");
                edtPhone.equals("Not Applicable");
                Service = "Only Cleaning";

                edtAddress.setText(Address);

                //Substring
                String House_Number = Address.substring(0,Address.indexOf(' '));

                //Convert into integer
                HouseNumber = Integer.parseInt(House_Number.toString());



                //Toast.makeText(this, "Company Name: " + CompanyName,Toast.LENGTH_LONG).show();

            }



        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }




}
