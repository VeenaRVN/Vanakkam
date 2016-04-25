package com.vanakkam.rvnve.vanakkam;

public class RegisterData {

    //private variables
    int _id;
    String name;
    String email_id;
    String password;

    // Empty constructor
    public RegisterData() {
    }

    // constructor
    public RegisterData(int id, String name, String email_id, String password) {
        this._id = id;
        this.name = name;
        this.email_id = email_id;
        this.password = password;
    }

    // get ID
    public int getID() {
        return this._id;
    }

    // set ID
    public void setID(int id) {
        this._id = id;
    }

    // get Name
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    // set first name
    public void setName(String name) {
        this.name = name;
    }


    // get EmailId
    public String getEmailId() {
        // TODO Auto-generated method stub
        return email_id;
    }

    // Set LastName
    public void setEmailId(String email_id) {
        this.email_id = email_id;
    }


    // get password
    public String getPassword() {
        return password;
    }

    // set password
    public void setPassword(String password) {
        this.password = password;
    }
}