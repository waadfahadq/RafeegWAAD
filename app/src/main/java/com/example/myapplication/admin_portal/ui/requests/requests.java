package com.example.myapplication.admin_portal.ui.requests;

public class requests {
    private String id,email,description;
    public requests(){

    }
    public requests(String id,String email, String description){
        this.id = id;
        this.email = email;
        this.description =description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
