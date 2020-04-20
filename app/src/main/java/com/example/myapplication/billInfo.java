package com.example.myapplication;

public class billInfo {



    private String id;
    private String username;
    private String url;
    private String nameOfBill;
    private String dayOfWeek;
    private String date;



    public billInfo(String id , String username , String url , String nameOfBill , String dayOfWeek , String date ) {

        this.id = id;
        this.username = username;
        this.url = url;
        this.nameOfBill = nameOfBill;
        this.dayOfWeek= dayOfWeek;
        this.date = date;

    }


    public billInfo(){

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameOfBill() {
        return nameOfBill;
    }

    public void setNameOfBill(String nameOfBill) {
        this.nameOfBill = nameOfBill;
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

}
