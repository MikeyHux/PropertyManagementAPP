package com.pmapp.mikeys.propertymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCompaniesActivity extends AppCompatActivity {

    Boolean CompanyDoesExist;
    String Services;
    String CompanyName;
    String OnlyCleaning;
    String ManagementCleaning;
    String CompanyNameExist;
    String Command = "";
    String SelectedValue;
    EditText edtTextCompanyName;
    RadioGroup rdGrpCleaningOrManagement;
    RadioButton rdbtnManagementCleaning;
    RadioButton rdbtnCleaning;
    RadioButton rdbSelected;
    Button btnAddCompanyName;
    Button btnCancelCompanyName;
    int selectedId;


    int COMPANY_ID;

    CompaniesDatabaseHandler CompdbHandler = new CompaniesDatabaseHandler(AddCompaniesActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompanies);

        edtTextCompanyName = (EditText) findViewById(R.id.edtTextCompanyName);
        edtTextCompanyName.setText("");
         btnAddCompanyName = (Button) findViewById(R.id.btnAddCompanyName);
         btnCancelCompanyName = (Button) findViewById(R.id.btnCancelCompanyName);

        rdbtnManagementCleaning = (RadioButton) findViewById(R.id.rdbtnManagementCleaning);
        rdbtnCleaning = (RadioButton) findViewById(R.id.rdbtnCleaning);



        try {

            Command = getIntent().getStringExtra("Command");

            Toast.makeText(this, "called from!: " + Command, Toast.LENGTH_LONG).show();

            if(Command.equals("EDIT")){

                //String Company_ID = getIntent().getStringExtra("COMPANY_ID");
                COMPANY_ID = Integer.parseInt(getIntent().getStringExtra("COMPANY_ID"));
                Companies companies = CompdbHandler.Get_Companies(COMPANY_ID);
                CompanyName = companies.getCompanyName();
                edtTextCompanyName.setText(CompanyName);
                Services = companies.getServices().trim();
                btnAddCompanyName.setText("SAVE");

                if(Services.equals("Management and Cleaning")){

                    rdbtnManagementCleaning.setChecked(true);


                } else {

                    rdbtnCleaning.setChecked(true);

                }

            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);

        }


        btnCancelCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCompaniesActivity.this, CompaniesDatabaseActivity.class));

                Toast.makeText(AddCompaniesActivity.this, "User Cancels!", Toast.LENGTH_LONG).show();

            }
        });

        //ADD A company to SQL
        btnAddCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CompanyDoesExist = false;

                CompanyName = edtTextCompanyName.getText().toString().trim();
                rdGrpCleaningOrManagement = (RadioGroup) findViewById(R.id.rdGrpCleaningOrManagement);
                selectedId = rdGrpCleaningOrManagement.getCheckedRadioButtonId();
                rdbSelected = (RadioButton) findViewById(selectedId);



                if (selectedId != -1 && Command.equals("ADD") && !CompanyName.isEmpty()) {

                    //Toast.makeText(AddCompaniesActivity.this, "Command ADD!", Toast.LENGTH_LONG).show();

                    Services = rdbSelected.getText().toString();


                   // Toast.makeText(AddCompaniesActivity.this, "Services!" + Services, Toast.LENGTH_LONG).show();

                    //AddCompany();

                    CheckCompany();

                    AddCompany();

                   // Toast.makeText(AddCompaniesActivity.this, "CompanyDoesExist!" + CompanyDoesExist, Toast.LENGTH_LONG).show();



                } else {

                    if(Command.equals("EDIT")){

                        Services = rdbSelected.getText().toString();
                        CompdbHandler.Update_Companies(new Companies(COMPANY_ID, CompanyName, Services));
                        CompdbHandler.close();

                        Toast.makeText(AddCompaniesActivity.this, "Company UPDATED!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(AddCompaniesActivity.this, CompaniesDatabaseActivity.class));



                    } else {

                        Toast.makeText(AddCompaniesActivity.this, "Company Name is Empty and/or Select Cleaning or Cleaning and Management - Please make sure its not empty!", Toast.LENGTH_LONG).show();

                    }



                }

            }
        });

    }


    public void CheckCompany(){

        CompaniesDatabaseHandler db = new CompaniesDatabaseHandler(AddCompaniesActivity.this);
        ArrayList<Companies> companies_array_from_db = db.Get_Companies();

        for (int i = 0; i < companies_array_from_db.size(); i++) {

        int CompID = companies_array_from_db.get(i).getID();
        CompanyNameExist = companies_array_from_db.get(i).getCompanyName().trim();

        if(CompanyName.equals(CompanyNameExist)){

            CompanyDoesExist = true;

            }

        };
        }

    public void AddCompany(){

        if(CompanyDoesExist.equals(false)){

            CompdbHandler.Add_Companies(new Companies(CompanyName, Services));

            //  CompanyName = CompanyName + " - " + Services;

            Toast.makeText(AddCompaniesActivity.this, "Company Added!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(AddCompaniesActivity.this, CompaniesDatabaseActivity.class));

        } else {

            Toast.makeText(AddCompaniesActivity.this, "Company EXIST!", Toast.LENGTH_LONG).show();
            edtTextCompanyName.setText("");



        }

    }

    }



