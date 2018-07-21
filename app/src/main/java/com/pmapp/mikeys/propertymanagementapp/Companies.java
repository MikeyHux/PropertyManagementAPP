package com.pmapp.mikeys.propertymanagementapp;

public class Companies {

    // private variables
    public int _id;
    public String _companyname;

    public Companies() {
    }

    // constructor
    public Companies(int id, String CompanyName) {
        this._id = id;
        this._companyname = CompanyName;

    }

    // constructor
    public Companies(String CompanyName) {
        this._companyname = CompanyName;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getCompanyName() {
        return this._companyname;
    }

    // setting name
    public void setCompanyName(String CompanyName) { this._companyname = CompanyName;}

}
