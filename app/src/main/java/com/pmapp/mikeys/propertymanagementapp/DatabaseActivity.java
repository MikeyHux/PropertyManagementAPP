package com.pmapp.mikeys.propertymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DatabaseActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);




        //Toast.makeText(this, " We Are in Database Activity!",Toast.LENGTH_LONG).show();

        Button btnCompanies = (Button)findViewById(R.id.btnCompanies);

        Button btnProperties = (Button)findViewById(R.id.btnProperties);

        Button btnCleaners = (Button)findViewById(R.id.btnCleaners);

        btnCompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DatabaseActivity.this, "GO to Companies Database Activity!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DatabaseActivity.this, CompaniesDatabaseActivity.class));
            }
        });

        btnProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DatabaseActivity.this, "GO to Properties Database Activity!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DatabaseActivity.this, PropertiesDatabaseActivity.class));
            }
        });

        btnCleaners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DatabaseActivity.this, "GO to Properties Database Activity!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DatabaseActivity.this, CleanersDatabaseActivity.class));
            }
        });




    }







}
