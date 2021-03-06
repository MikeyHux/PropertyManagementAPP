package com.pmapp.mikeys.propertymanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertiesDatabaseActivity extends AppCompatActivity {

    ListView Properties_listview;
    ArrayList<Properties> properties_data = new ArrayList<Properties>();
    Properties_Adapter pAdapter;
    PropertiesDatabaseHandler db;

    List propertiesList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertiesdatabase);

        try {
            Properties_listview = (ListView) findViewById(R.id.Propertieslist);
            Properties_listview.setItemsCanFocus(false);

            RefreshData();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }




        Button add_btnProperties = (Button)findViewById(R.id.add_btnProperties);
        add_btnProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // startActivity(new Intent(CompaniesDatabaseActivity.this, AddCompaniesActivity.class));


                Intent intent = new Intent(getApplicationContext(), AddPropertiesActivity.class);
                intent.putExtra("Command", "ADD");
                startActivity(intent);
            }
        });



    }

    public void RefreshData(){

        properties_data.clear();

        db = new PropertiesDatabaseHandler(this);

        ArrayList<Properties> properties_array_from_db = db.Get_Properties();




        for (int i = 0; i < properties_array_from_db.size(); i++) {

            int PropertyID = properties_array_from_db.get(i).getID();
            String CompanyName = properties_array_from_db.get(i).getCompanyName();
            String Services = properties_array_from_db.get(i).getServices();
            String OwnerName = properties_array_from_db.get(i).getOwnerName();
            String Address = properties_array_from_db.get(i).getAddress();
            //Address = Address.substring(0, Address.indexOf(' '));
            String Email = properties_array_from_db.get(i).getEmail();
            String Phone = properties_array_from_db.get(i).getPhone();

            Toast.makeText(this, "Address!" + Address, Toast.LENGTH_LONG).show();


            Properties properties = new Properties();
            properties.setID(PropertyID);
            properties.setCompanyName(CompanyName);
            properties.setServices(Services);
            properties.setOwnerName(OwnerName);
            properties.setAddress(Address);
            properties.setEmail(Email);
            properties.setPhone(Phone);

            properties_data.add(properties);


        }

        db.close();

        pAdapter = new Properties_Adapter(PropertiesDatabaseActivity.this, R.layout.listview_properties, properties_data);
        Properties_listview.setAdapter(pAdapter);

    }





    public class Properties_Adapter extends ArrayAdapter<Properties> {
        Activity activity;
        int layoutResourceId;
        Properties user; //user

        ArrayList<Properties> data = new ArrayList <Properties>();

        public Properties_Adapter(Activity act, int layoutResourceId, ArrayList<Properties> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            //  notifyDataSetChanged();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new PropertiesDatabaseActivity.Properties_Adapter.UserHolder();
                holder.CompanyName = (TextView) row.findViewById(R.id.txtCompanyName);
                holder.Services = (TextView) row.findViewById(R.id.txtServices);
                holder.OwnerName = (TextView) row.findViewById(R.id.txtOwnerName);
                holder.Address = (TextView) row.findViewById(R.id.txtAddress);
                holder.Email = (TextView) row.findViewById(R.id.txtEmail);
                holder.Phone = (TextView) row.findViewById(R.id.txtPhone);
                holder.edit = (Button) row.findViewById(R.id.btn_update);
                holder.delete = (Button) row.findViewById(R.id.btn_delete);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            user = data.get(position);
            holder.edit.setTag(user.getID());
            holder.delete.setTag(user.getID());
            holder.CompanyName.setText(user.getCompanyName());
            // holder.Services.setText("-" + user.getServices());
            holder.Services.setText(user.getServices());
            holder.OwnerName.setText(user.getOwnerName());
            holder.Address.setText(user.getAddress());
            holder.Email.setText(user.getEmail());
            holder.Phone.setText(user.getPhone());

            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_properties = new Intent(activity, AddPropertiesActivity.class);
                    update_properties.putExtra("Command", "EDIT");
                    update_properties.putExtra("PROPERTY_ID", v.getTag().toString());
                    activity.startActivity(update_properties);

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub

                    // show a message while loader is loading

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int property_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Toast.makeText(PropertiesDatabaseActivity.this, "Property ID" + property_id, Toast.LENGTH_LONG).show();
                                    // MyDataObject.remove(positionToRemove);
                                    PropertiesDatabaseHandler dBHandler = new PropertiesDatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_Properties(property_id);

                                    //CompaniesDatabaseActivity.this.onResume();
                                    //Refresh screen so when its deleted it shows an updated list
                                    finish();
                                    startActivity(getIntent());

                                }
                            });
                    adb.show();
                }

            });
            return row;

        }

        class UserHolder {
            TextView CompanyName;
            TextView Services;
            TextView OwnerName;
            TextView Address;
            TextView Email;
            TextView Phone;
            Button edit;
            Button delete;
        }
    }





    @Override
    public void onBackPressed()
    {
        // do stuff
        //Toast.makeText(this, "Back Button is pressed!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
        startActivity(intent);
        super.onBackPressed();


    }


}
