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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public storeinfo() {
    }

    public storeinfo(String name, int num, String email, String beaconID, String typeStore, String image, String id) {
        this.name = name;
        this.num = num;
        this.email = email;
        this.beaconID = beaconID;
        this.typeStore = typeStore;
        this.image = image;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTypeStore() {
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
