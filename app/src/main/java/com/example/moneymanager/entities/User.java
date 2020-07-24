package com.example.moneymanager.entities;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String password;
    private String securityAnswer;

    @Ignore
    public User(String name, String email, String password, String securityAnswer){
        this.name = name;
        this.email = email;
        this.password = password;
        this.securityAnswer = securityAnswer;
    }

    public User(int id, String name, String email, String password, String securityAnswer){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.securityAnswer = securityAnswer;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + email + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
