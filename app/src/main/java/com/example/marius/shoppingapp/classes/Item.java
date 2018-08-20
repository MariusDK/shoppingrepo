package com.example.marius.shoppingapp.classes;

public class Item {
    public String name;
    public int quantity;
    public String id_user;

    public Item() {
    }

    public Item(String name, int quantity, String id_user) {
        this.name = name;
        this.quantity = quantity;
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
