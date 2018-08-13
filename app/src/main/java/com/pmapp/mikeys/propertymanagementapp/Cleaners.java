package com.pmapp.mikeys.propertymanagementapp;

public class Cleaners {

    // private variables
    public int _id;
    public String _cleanername;
    public String _address;
    public int _house_number;
    public String _email;
    public String _phone;

    public Cleaners() {
    }

    // constructor
    public Cleaners(int id, String CleanerName, String Address, int HouseNumber, String Email, String Phone ) {
        this._id = id;
        this._cleanername = CleanerName;
        this._address = Address;
        this._house_number = HouseNumber;
        this._email = Email;
        this._phone = Phone;

    }

    // constructor
    public Cleaners(String CleanerName, String Address, int HouseNumber, String Email, String Phone) {
        this._cleanername = CleanerName;
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
    public String getCleanerName() {
        return this._cleanername;
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
    public void setCleanerName(String CleanerName) { this._cleanername = CleanerName;}
    public void setAddress(String Address) { this._address = Address;}
    public void setHouseNumber (int HouseNumber) {
        this._house_number = HouseNumber;
    }
    public void setEmail(String Email) { this._email = Email;}
    public void setPhone(String Phone) { this._phone = Phone;}






}

