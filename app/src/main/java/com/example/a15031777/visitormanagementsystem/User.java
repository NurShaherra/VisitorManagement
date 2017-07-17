package com.example.a15031777.visitormanagementsystem;

/**
 * Created by 15031777 on 18/7/2017.
 */

public class User {

    private int id;
    private String username;
    private String email;
    private String userRole;
    private String fullname;
    private String unitAddress;
    private String blocknum;

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getBlocknum() {
        return blocknum;
    }

    public void setBlocknum(String blocknum) {
        this.blocknum = blocknum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}