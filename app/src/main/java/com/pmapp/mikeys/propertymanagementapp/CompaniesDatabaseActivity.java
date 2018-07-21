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

import com.pmapp.mikeys.propertymanagementapp.R;

import java.util.ArrayList;

public class CompaniesDatabaseActivity extends AppCompatActivity {

    ListView Companies_listview;
    ArrayList<Companies> companies_data = new ArrayList<Companies>();
    Companies_Adapter cAdapter;
    CompaniesDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companiesdatabase);


       // Toast.makeText(this, "Companies Database Activity!", Toast.LENGTH_LONG).show();

        try {
            Companies_listview = (ListView) findViewById(R.id.Companieslist);
            Companies_listview.setItemsCanFocus(false);

           RefreshData();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }

        Button add_btnCompanies = (Button)findViewById(R.id.add_btnCompanies);

        add_btnCompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(CompaniesDatabaseActivity.this, AddCompaniesActivity.class));


                Intent intent = new Intent(getApplicationContext(), AddCompaniesActivity.class);
                intent.putExtra("ADD", "ADD");
                startActivity(intent);
            }
        });



    }

    public void RefreshData(){
        companies_data.clear();
        db = new CompaniesDatabaseHandler(this);
        ArrayList<Companies> companies_array_from_db = db.Get_Companies();

        for (int i = 0; i < companies_array_from_db.size(); i++) {

            int CompanyID = companies_array_from_db.get(i).getID();
            String CompanyName = companies_array_from_db.get(i).getCompanyName();

            Companies companies = new Companies();
            companies.setID(CompanyID);
            companies.setCompanyName(CompanyName);


            companies_data.add(companies);
        }
        db.close();
        cAdapter = new Companies_Adapter(CompaniesDatabaseActivity.this, R.layout.listview_row, companies_data);
        Companies_listview.setAdapter(cAdapter);

    }

    public class Companies_Adapter extends ArrayAdapter<Companies> {
        Activity activity;
        int layoutResourceId;
        Companies user; //user
        ArrayList<Companies> data = new ArrayList<Companies>();

        public Companies_Adapter(Activity act, int layoutResourceId,
                               ArrayList<Companies> data) {
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
                holder = new UserHolder();
                holder.CompanyName = (TextView) row.findViewById(R.id.txtCompanyName);
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

            holder.edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_company = new Intent(activity,
                            AddCompaniesActivity.class);
                    update_company.putExtra("Edit", "Edit");
                    update_company.putExtra("COMPANY_ID", v.getTag().toString());
                    activity.startActivity(update_company);

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
                    final int company_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Toast.makeText(CompaniesDatabaseActivity.this, "Company ID" + company_id, Toast.LENGTH_LONG).show();
                                    // MyDataObject.remove(positionToRemove);
                                    CompaniesDatabaseHandler dBHandler = new CompaniesDatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_Companies(company_id);

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
            Button edit;
            Button delete;
        }

    }


}
