package com.pmapp.mikeys.propertymanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class CleanersDatabaseActivity extends AppCompatActivity {

    ListView Cleaners_listview;
    ArrayList<Cleaners> cleaners_data = new ArrayList<Cleaners>();
    Cleaners_Adapter pAdapter;
    CleanersDatabaseHandler db;

    List cleanersList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleanersdatabase);

        try {
            Cleaners_listview = (ListView) findViewById(R.id.Cleanerslist);
            Cleaners_listview.setItemsCanFocus(false);

            RefreshData();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }




        Button add_btnCleaners = (Button)findViewById(R.id.add_btnCleaners);
        add_btnCleaners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // startActivity(new Intent(CompaniesDatabaseActivity.this, AddCompaniesActivity.class));


                Intent intent = new Intent(getApplicationContext(), AddCleanersActivity.class);
                intent.putExtra("Command", "ADD");
                startActivity(intent);
            }
        });



    }

    public void RefreshData(){

        cleaners_data.clear();

        db = new CleanersDatabaseHandler(this);

        ArrayList<Cleaners> cleaners_array = db.Get_Cleaners();




        for (int i = 0; i < cleaners_array.size(); i++) {

            int CleanerID = cleaners_array.get(i).getID();
            String CleanerName = cleaners_array.get(i).getCleanerName();
            String Address = cleaners_array.get(i).getAddress();
            //Address = Address.substring(0, Address.indexOf(' '));
            String Email = cleaners_array.get(i).getEmail();
            String Phone = cleaners_array.get(i).getPhone();

            Toast.makeText(this, "Cleaners!" + Address, Toast.LENGTH_LONG).show();


            Cleaners cleaners = new Cleaners();
            cleaners.setID(CleanerID);
            cleaners.setCleanerName(CleanerName);
            cleaners.setAddress(Address);
            cleaners.setEmail(Email);
            cleaners.setPhone(Phone);

            cleaners_data.add(cleaners);


        }

        db.close();

        pAdapter = new Cleaners_Adapter(CleanersDatabaseActivity.this, R.layout.listview_cleaners, cleaners_data);
        Cleaners_listview.setAdapter(pAdapter);

    }





    public class Cleaners_Adapter extends ArrayAdapter<Cleaners> {
        Activity activity;
        int layoutResourceId;
        Cleaners user; //user

        ArrayList<Cleaners> data = new ArrayList <Cleaners>();

        public Cleaners_Adapter(Activity act, int layoutResourceId, ArrayList<Cleaners> data) {
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
                holder = new CleanersDatabaseActivity.Cleaners_Adapter.UserHolder();
                holder.CleanerName = (TextView) row.findViewById(R.id.txtCleanerName);
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
            holder.CleanerName.setText(user.getCleanerName());
            holder.Address.setText(user.getAddress());
            holder.Email.setText(user.getEmail());
            holder.Phone.setText(user.getPhone());

            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_cleaners = new Intent(activity, AddCleanersActivity.class);
                    update_cleaners.putExtra("Command", "EDIT");
                    update_cleaners.putExtra("CLEANER_ID", v.getTag().toString());
                    activity.startActivity(update_cleaners);

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
                    final int cleaner_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Toast.makeText(CleanersDatabaseActivity.this, "Cleaner ID" + cleaner_id, Toast.LENGTH_LONG).show();
                                    // MyDataObject.remove(positionToRemove);
                                    CleanersDatabaseHandler dBHandler = new CleanersDatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_Cleaners(cleaner_id);

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
            TextView CleanerName;
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
