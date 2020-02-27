package com.example.myapplication.ui.dashboard;

public class checklistModel implements java.io.Serializable {
    private String productname,quantity;

    public checklistModel(){    }

    public checklistModel(String pName, String q){
        this.productname = pName;
        this.quantity = q;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
