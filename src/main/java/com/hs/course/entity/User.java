package com.hs.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int id;
    String name;
    String pwd;
    String className;
    String studentID;
    String phone;
    Timestamp date;
    int role;

    public User(String name, String pwd, String phone, int role) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.role = role;
    }
}
