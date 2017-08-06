package com.example.a15031777.visitormanagementsystem;

/**
 * Created by HP on 7/8/2017.
 */

public class Visitor {
    private int id;
    private String fullname;
    private String email;
    private String transportmode;
    private String userId;

    public Visitor(int id, String fullname, String email, String transportmode, String userId) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.transportmode = transportmode;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTransportmode() {
        return transportmode;
    }

    public void setTransportmode(String transportmode) {
        this.transportmode = transportmode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
