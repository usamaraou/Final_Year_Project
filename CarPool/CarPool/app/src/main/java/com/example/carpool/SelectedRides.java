package com.example.carpool;

public class SelectedRides {

    private String Driver_Name, Driver_Number;
    private String Rider_Name, Rider_Number;
    private String Rider_Destination, Rider_Pickup;

    public SelectedRides() {
    }

    public SelectedRides(String driver_Name, String driver_Number, String rider_Name, String rider_Number, String rider_Destination, String rider_Pickup) {
        Driver_Name = driver_Name;
        Driver_Number = driver_Number;
        Rider_Name = rider_Name;
        Rider_Number = rider_Number;
        Rider_Destination = rider_Destination;
        Rider_Pickup = rider_Pickup;
    }

    public String getDriver_Name() {
        return Driver_Name;
    }

    public void setDriver_Name(String driver_Name) {
        Driver_Name = driver_Name;
    }

    public String getDriver_Number() {
        return Driver_Number;
    }

    public void setDriver_Number(String driver_Number) {
        Driver_Number = driver_Number;
    }

    public String getRider_Name() {
        return Rider_Name;
    }

    public void setRider_Name(String rider_Name) {
        Rider_Name = rider_Name;
    }

    public String getRider_Number() {
        return Rider_Number;
    }

    public void setRider_Number(String rider_Number) {
        Rider_Number = rider_Number;
    }

    public String getRider_Destination() {
        return Rider_Destination;
    }

    public void setRider_Destination(String rider_Destination) {
        Rider_Destination = rider_Destination;
    }

    public String getRider_Pickup() {
        return Rider_Pickup;
    }

    public void setRider_Pickup(String rider_Pickup) {
        Rider_Pickup = rider_Pickup;
    }
}
