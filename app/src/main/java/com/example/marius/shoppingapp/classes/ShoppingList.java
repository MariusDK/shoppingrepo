package com.example.marius.shoppingapp.classes;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;
@IgnoreExtraProperties
public class ShoppingList {
    private String nume;
    private String id_user;
    private ArrayList<String> itemList;

    public ShoppingList() {
    }

    public ShoppingList(String nume, String id_user, ArrayList<String> itemList) {
        this.nume = nume;
        this.id_user = id_user;
        this.itemList = itemList;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<String> itemList) {
        this.itemList = itemList;
    }
}
