package com.example.luismillan.pymbil;

import android.graphics.Bitmap;

/**
 * Created by luismillan on 9/19/15.
 */
public class Items {
    public String title;
    public String descriprion;
    public String latitud,longitud;
    public Bitmap photo;


    public Items (String title){

        this.title = title;
    }


    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getDescriprion() {

        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
