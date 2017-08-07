package com.example.a15031777.visitormanagementsystem;

/**
 * Created by HP on 7/8/2017.
 */
/* DONE BY 15017484 */
public class Visitor {
    private int id;
    private String fullname;
    private String email;
    private int mobile;
    private String transportmode;
    private int sign_in;
    private int userId;

    public Visitor(int id, String fullname, String email, int mobile, String transportmode, int sign_in, int userId) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.transportmode = transportmode;
        this.sign_in = sign_in;
        this.userId = userId;
        this.mobile = mobile;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getSign_in() {
        return sign_in;
    }

    public void setSign_in(int sign_in) {
        this.sign_in = sign_in;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
