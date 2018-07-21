package com.pmapp.mikeys.propertymanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toast.makeText(this, "Settings Activity!",
                Toast.LENGTH_LONG).show();

        //Declare button
        //Button btnOwnerProfiles = (Button)findViewById(R.id.btnOwnerProfiles);
        Button btnCleaningSettings = (Button)findViewById(R.id.btnCleaningSettings);
        Button btnManagementSettings = (Button)findViewById(R.id.btnManagementSettings);
        Button btnDatabaseSettings = (Button)findViewById(R.id.btnDatabaseSettings);

        btnCleaningSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(SettingsActivity.this, "GO to Cleaning Setting Activity!", Toast.LENGTH_LONG).show();
            }
        });

        btnManagementSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(SettingsActivity.this, "GO to Management Setting Activity!", Toast.LENGTH_LONG).show();
            }
        });

        btnDatabaseSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(SettingsActivity.this, "GO to Database Setting Activity!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SettingsActivity.this, DatabaseActivity.class));
            }
        });




        //On click listener once btnSettings is clicked we will be directed to the Settings Activity
        /*  btnOwnerProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, OwnerProfilesActivity.class));
            }
        });

        */



    }

}
