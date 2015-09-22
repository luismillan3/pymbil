package com.example.luismillan.pymbil;

/**
 * Created by luismillan on 9/20/15.
 */
public class UserProfile {
    String name;
    String phone;
    String wallet;
    String ID;


    public UserProfile(String ID,String name, String phone, String wallet) {
        this.name = name;
        this.phone = phone;
        this.wallet = wallet;
        this.ID = ID;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
