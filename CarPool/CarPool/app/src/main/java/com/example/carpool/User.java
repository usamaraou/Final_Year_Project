package com.example.carpool;

public class User {

    private String Name, Number;
    private String Destination, PickUp_Point;
    private String Key;

    public User() {
    }

    public User(String name, String number, String destination, String pickUp_Point, String key) {
        Name = name;
        Number = number;
        Destination = destination;
        PickUp_Point = pickUp_Point;
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getPickUp_Point() {
        return PickUp_Point;
    }

    public void setPickUp_Point(String pickUp_Point) {
        PickUp_Point = pickUp_Point;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}