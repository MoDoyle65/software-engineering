package com.example.temp2015.sign_in_and_ui;

/**
 * Created by temp2015 on 14-Dec-16.
 */

public class User {
    private String name;
    private String email;
    private String uid;

    public User() {
    }

    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;

    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getUid() {
        return uid;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }


}
