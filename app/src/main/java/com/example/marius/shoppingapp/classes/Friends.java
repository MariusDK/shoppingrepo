package com.example.marius.shoppingapp.classes;

public class Friends{

    private String email;
    private String id_user_list;

    public Friends() {
    }

    public Friends(String email, String id_user_list) {
        this.email = email;
        this.id_user_list = id_user_list;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_user_list() {
        return id_user_list;
    }

    public void setId_user_list(String id_user_list) {
        this.id_user_list = id_user_list;
    }
}
