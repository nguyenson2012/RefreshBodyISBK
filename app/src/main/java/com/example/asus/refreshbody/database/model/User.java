package com.example.asus.refreshbody.database.model;

/**
 * Created by Asus on 10/14/2016.
 */

public class User{
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_TABLE="user_table";
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
