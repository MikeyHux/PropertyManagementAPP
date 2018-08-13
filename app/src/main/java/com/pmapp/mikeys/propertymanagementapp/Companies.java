package com.pmapp.mikeys.propertymanagementapp;

public class Companies {

    // private variables
    public int _id;
    public String _companyname;
    public String _services;

    public Companies() {
    }

    // constructor
    public Companies(int id, String CompanyName, String Services ) {
        this._id = id;
        this._companyname = CompanyName;
        this._services = Services;

    }

    // constructor
    public Companies(String CompanyName,String Services) {
        this._companyname = CompanyName;
        this._services = Services;
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
    public String getServices() {
        return this._services;
    }

    // setting name
    public void setCompanyName(String CompanyName) { this._companyname = CompanyName;}
    public void setServices(String Services) { this._services = Services;}

}
