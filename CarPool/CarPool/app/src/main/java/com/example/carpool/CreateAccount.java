package com.example.carpool;

public class CreateAccount {
    private String name;
    private  String id;
    private  String email;
    private  String number;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public CreateAccount() {
    }

    public CreateAccount(String name, String id, String email, String number, String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.number = number;
        this.password = password;
    }


}
