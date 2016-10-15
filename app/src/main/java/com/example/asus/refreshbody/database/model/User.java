package com.example.asus.refreshbody.database.model;

import io.realm.RealmObject;

/**
 * Created by Asus on 10/14/2016.
 */

public class User extends RealmObject {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
