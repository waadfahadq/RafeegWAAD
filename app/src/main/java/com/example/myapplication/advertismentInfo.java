package com.example.myapplication;

public class advertismentInfo {


    private String id;
    private String username;
    private String NameOfBeacon;
    private String description;
    private String dayOfWeek;
    private String date;
    private String shopName ;



    public advertismentInfo(String id , String username , String nameOfBeacon, String description   , String date , String dayOfWeek , String shopName) {

        this.id = id;
        this.username = username;
        this.NameOfBeacon = nameOfBeacon;
        this.description = description;
        this.date = date;
        this.dayOfWeek= dayOfWeek;
        this.shopName = shopName ;
    }


    public advertismentInfo(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameOfBeacon() {
        return NameOfBeacon;
    }

    public void setNameOfBeacon(String nameOfBeacon) {
        NameOfBeacon = nameOfBeacon;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
