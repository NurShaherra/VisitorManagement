package com.example.a15031777.visitormanagementsystem;

/**
 * Created by 15017199 on 7/8/2017.
 */

public class Report {
    private String reportId;
    private String managerName;
    private String numPeopleSignedIn;
    private String createdDate;

    public Report() {

    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getNumPeopleSignedIn() {
        return numPeopleSignedIn;
    }

    public void setNumPeopleSignedIn(String numPeopleSignedIn) {
        this.numPeopleSignedIn = numPeopleSignedIn;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
