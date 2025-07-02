package com.example.nonfatalfirearminjuries2;

public class ReportModel {
    private String date, location, severity;

    public ReportModel(String date, String location, String severity) {
        this.date = date;
        this.location = location;
        this.severity = severity;

    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getSeverity() {
        return severity;
    }
}

