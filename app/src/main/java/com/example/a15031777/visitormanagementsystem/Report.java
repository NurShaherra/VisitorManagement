package com.example.a15031777.visitormanagementsystem;

/**
 * Created by 15017199 on 7/8/2017.
 */

public class Report {
    private int reportId;
    private String managerName;
    private int numPeopleSignedIn;
    private String createdDate;

    public Report() {

    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getNumPeopleSignedIn() {
        return numPeopleSignedIn;
    }

    public void setNumPeopleSignedIn(int numPeopleSignedIn) {
        this.numPeopleSignedIn = numPeopleSignedIn;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
