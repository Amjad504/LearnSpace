package com.example.learnspace;

public class Student_Profile {

    String name;
    String details;
    String gender;
    String email;

    String speciality;

    String role;


    public Student_Profile(String name, String details, String gender, String email, String speciality, String role) {
        this.name = name;
        this.details = details;
        this.gender = gender;
        this.email = email;
        this.speciality = speciality;
        this.role = role;
    }

    public Student_Profile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


