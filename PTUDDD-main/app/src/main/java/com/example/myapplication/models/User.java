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
    private String idCard;
    private String email;
    private String phone;
    private String role;

    private String studentCode;
    private String teacherId;

    // Constructors
    public User() {
    }

    public User(String username, String password, String fullName, String gender, String address, String placeOfBirth, String dateOfBirth, String idCard, String email, String phone, String role, String studentCode, String teacherId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.idCard = idCard;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.studentCode = studentCode;
        this.teacherId = teacherId;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

}
