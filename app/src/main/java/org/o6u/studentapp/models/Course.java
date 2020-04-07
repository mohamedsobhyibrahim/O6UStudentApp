package org.o6u.studentapp.models;

import java.io.Serializable;

public class Course implements Serializable {

    private String title;
    private String doctorName;
    private String department;
    private String hours;

    public Course(String title, String doctorName, String department, String hours) {
        this.title = title;
        this.doctorName = doctorName;
        this.department = department;
        this.hours = hours;
    }

    public Course() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
