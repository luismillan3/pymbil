package com.example.luismillan.pymbil;

import android.graphics.Bitmap;

/**
 * Created by luismillan on 9/19/15.
 */
public class Items {
    public String title;
    public String Id;
    public String date, phoneNumber;
    public String amount;


    public Items (String title){

        this.title = title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {

        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
