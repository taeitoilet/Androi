package com.example.myapplication.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private String address;
    private String placeOfBirth;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String role;

    private String patientCode;
    private String doctorId;

    // Constructors
    public User() {
    }

    public User(String username, String password, String fullName, String gender, String address, String placeOfBirth, String dateOfBirth,  String email, String phone, String role, String patientCode, String doctorId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.patientCode = patientCode;
        this.doctorId = doctorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String studentCode) {
        this.patientCode = studentCode;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String teacherId) {
        this.doctorId = teacherId;
    }

}
