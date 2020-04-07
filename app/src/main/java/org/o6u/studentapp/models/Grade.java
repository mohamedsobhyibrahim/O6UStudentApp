package org.o6u.studentapp.models;

import java.io.Serializable;

public class Grade implements Serializable {

    private String courseName;
    private String quizOne;
    private String quizTwo;
    private String midterm;
    private String section;
    private String attendance;
    private String practical;
    private String finalExam;
    private String total;

    public Grade(String courseName, String quizOne, String quizTwo, String midterm, String section,
                 String attendance, String practical, String finalExam, String total) {
        this.courseName = courseName;
        this.quizOne = quizOne;
        this.quizTwo = quizTwo;
        this.midterm = midterm;
        this.section = section;
        this.attendance = attendance;
        this.practical = practical;
        this.finalExam = finalExam;
        this.total = total;
    }

    public Grade() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getQuizOne() {
        return quizOne;
    }

    public void setQuizOne(String quizOne) {
        this.quizOne = quizOne;
    }

    public String getQuizTwo() {
        return quizTwo;
    }

    public void setQuizTwo(String quizTwo) {
        this.quizTwo = quizTwo;
    }

    public String getMidterm() {
        return midterm;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPractical() {
        return practical;
    }

    public void setPractical(String practical) {
        this.practical = practical;
    }

    public String getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(String finalExam) {
        this.finalExam = finalExam;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
