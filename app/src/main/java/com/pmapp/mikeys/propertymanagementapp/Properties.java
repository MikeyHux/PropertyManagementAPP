package com.pmapp.mikeys.propertymanagementapp;

public class Properties {

    // private variables
    public int _id;
    public String _companyname;
    public String _services;
    public String _ownername;
    public String _address;
    public int _house_number;
    public String _email;
    public String _phone;

    public Properties() {
    }

    // constructor
    public Properties(int id, String CompanyName, String Services, String OwnerName, String Address, int HouseNumber, String Email, String Phone ) {
        this._id = id;
        this._companyname = CompanyName;
        this._services = Services;
        this._ownername = OwnerName;
        this._address = Address;
        this._house_number = HouseNumber;
        this._email = Email;
        this._phone = Phone;

    }

    // constructor
    public Properties(String CompanyName, String Services, String OwnerName, String Address, int HouseNumber, String Email, String Phone) {
        this._companyname = CompanyName;
        this._services = Services;
        this._ownername = OwnerName;
        this._address = Address;
        this._house_number = HouseNumber;
        this._email = Email;
        this._phone = Phone;
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
    public String getOwnerName() {
        return this._ownername;
    }
    public String getAddress() {
        return this._address;
    }
    public int getHouseNumber() {
        return this._house_number;
    }
    public String getEmail() {
        return this._email;
    }
    public String getPhone() {
        return this._phone;
    }

    // setting name
    public void setCompanyName(String CompanyName) { this._companyname = CompanyName;}
    public void setServices(String Services) { this._services = Services;}
    public void setOwnerName(String OwnerName) { this._ownername = OwnerName;}
    public void setAddress(String Address) { this._address = Address;}
    public void setHouseNumber (int HouseNumber) {
        this._house_number = HouseNumber;
    }
    public void setEmail(String Email) { this._email = Email;}
    public void setPhone(String Phone) { this._phone = Phone;}






}
