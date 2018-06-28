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
        Button btnOwnerProfiles = (Button)findViewById(R.id.btnOwnerProfiles);

        //On click listener once btnSettings is clicked we will be directed to the Settings Activity
        btnOwnerProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, OwnerProfilesActivity.class));
            }
        });


    }

}
