package com.example.myapplication.shopowner;

public class shopowner_info {
    private String name , email ;
    private boolean active;

    public shopowner_info(){
    }
    public shopowner_info(String name, String email, boolean active) {
        this.name = name;
        this.email = email;
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
