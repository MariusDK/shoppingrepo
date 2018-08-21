package com.example.marius.shoppingapp.classes;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@IgnoreExtraProperties
public class ShoppingList {
    private String nume;
    private String location;
    private String description;
    private Long date;
    private String id_user;
    private boolean status;
    @Exclude
    private String idList;

    public ShoppingList() {
    }

    public ShoppingList(String nume, String location, String description, Long date, String id_user, ArrayList<String> itemList, boolean status) {
        this.nume = nume;
        this.location = location;
        this.description = description;
        this.date = date;
        this.id_user = id_user;
        this.status = status;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}
