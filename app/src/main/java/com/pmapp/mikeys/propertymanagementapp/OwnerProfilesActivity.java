package com.pmapp.mikeys.propertymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OwnerProfilesActivity  extends AppCompatActivity {

    Button btnAddOwnerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerprofiles);

        Toast.makeText(this, "Owner Profiles Activity!",
                Toast.LENGTH_LONG).show();

        btnAddOwnerProfile = (Button) findViewById(R.id.btnAddOwnerProfile);

        btnAddOwnerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerProfilesActivity.this, AddUpdateOwnerProfile.class));
            }
        });


    }


}
