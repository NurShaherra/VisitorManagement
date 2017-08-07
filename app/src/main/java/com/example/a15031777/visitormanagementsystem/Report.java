package com.example.a15031777.visitormanagementsystem;

/**
 * Created by 15017199 on 7/8/2017.
 */

public class Report {
    private String managerName;
    private String createdDate;
    private int numSignedIn;

    public Report(){

    }

    public String getManagerName(){
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getNumSignedIn() {
        return numSignedIn;
    }

    public void setNumSignedIn(int numSignedIn) {
        this.numSignedIn = numSignedIn;
    }
}
