package com.example.myapplication.ui.home;

import java.io.Serializable;

public class storeinfo implements Serializable {
    String name ;
    int num;
    String email;
    String beaconID;
    String typeStore;
    String image;
    String id;
    private String from,to,floor,phone;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public storeinfo() {
    }

    public storeinfo(String name, int num, String email, String beaconID, String typeStore, String image, String id,String from,String to,String floor,String phone) {
        this.name = name;
        this.num = num;
        this.email = email;
        this.beaconID = beaconID;
        this.typeStore = typeStore;
        this.image = image;
        this.id=id;
        this.to=to;
        this.from=from;
        this.floor = floor;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public void setName(String name) {
        this.name = name;
    }
    public  String getName() {
        return name;
    }



    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) {
        this.beaconID = beaconID;
    }

    public  String getTypeStore() {
        return typeStore;
    }

    public void setTypeStore(String typeStore) {
        this.typeStore = typeStore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
