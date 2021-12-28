package com.health.my_heart.domain.model;


public class User {

    String inn;
    String name;
    String surname;
    String mail;
    String password;

    public User() {
        this.inn = "";
        this.name = "";
        this.surname = "";
        this.mail = "";
        this.password = "";
    }

    public User(String inn, String name, String surname, String mail, String password) {
        this.inn = inn;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
    }

    public String getInn() {
        return inn;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "inn='" + inn + '\'' +
                ", name='" + name + '\'' +
                ", fam='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
