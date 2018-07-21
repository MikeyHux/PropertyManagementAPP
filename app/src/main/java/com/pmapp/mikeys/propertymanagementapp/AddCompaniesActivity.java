package com.pmapp.mikeys.propertymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCompaniesActivity extends AppCompatActivity {

    String CompanyName;
    String CompanyNameExist;
    String Command = "";
    EditText edtTextCompanyName;
    int COMPANY_ID;

    CompaniesDatabaseHandler CompdbHandler = new CompaniesDatabaseHandler(AddCompaniesActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcompanies);

        edtTextCompanyName = (EditText)findViewById(R.id.edtTextCompanyName);
        edtTextCompanyName.setText("");
        Button btnAddCompanyName = (Button)findViewById(R.id.btnAddCompanyName);
        Button btnCancelCompanyName = (Button)findViewById(R.id.btnCancelCompanyName);
       // Toast.makeText(this, "Add Companies Database Activity!", Toast.LENGTH_LONG).show();

        Command = getIntent().getStringExtra("Edit");

        //Toast.makeText(this, "called from!: " + called_from , Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Company ID!: " + Company_ID , Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Command!: " + Command , Toast.LENGTH_LONG).show();

        if (Command != null){
            String Company_ID = getIntent().getStringExtra("COMPANY_ID");
            COMPANY_ID = Integer.parseInt(getIntent().getStringExtra("COMPANY_ID"));
            Companies companies = CompdbHandler.Get_Companies(COMPANY_ID);
            edtTextCompanyName.setText(companies.getCompanyName());

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


                CompanyName = edtTextCompanyName.getText().toString();

                if(CompanyName.isEmpty()){

                    Toast.makeText(AddCompaniesActivity.this, "Field is Empty - Please make sure its not empty!", Toast.LENGTH_LONG).show();

                } else {

                    AddCompany();

                }

            }

        });

       }

       public void AddCompany(){

        CompanyName = CompanyName.trim();

        CheckCompany();

        if(CompanyName.equals(CompanyNameExist)){

            Toast.makeText(AddCompaniesActivity.this, "Company Name Exist - Please choose another company name!", Toast.LENGTH_LONG).show();

            EditText edtTextCompanyName = (EditText)findViewById(R.id.edtTextCompanyName);
            edtTextCompanyName.setText("");


        } else {


            if (Command != null){

                CompdbHandler.Update_Companies(new Companies(COMPANY_ID, CompanyName));

                CompdbHandler.close();


            }else {

                CompdbHandler.Add_Companies(new Companies(CompanyName));

                Toast.makeText(AddCompaniesActivity.this, "Company Added!", Toast.LENGTH_LONG).show();

            }

            startActivity(new Intent(AddCompaniesActivity.this, CompaniesDatabaseActivity.class));
        }

       }

       public void CheckCompany(){

           CompaniesDatabaseHandler db = new CompaniesDatabaseHandler(this);
           ArrayList<Companies> companies_array_from_db = db.Get_Companies();

           for (int i = 0; i < companies_array_from_db.size(); i++) {

               int CompID = companies_array_from_db.get(i).getID();
               CompanyNameExist = companies_array_from_db.get(i).getCompanyName();



           }

       }

    }
