package com.example.myapplication.ui.dashboard;

public class checklistModel implements java.io.Serializable {
    private String productname,quantity,key;
    private boolean checked;
    public checklistModel(){    }

    public checklistModel(String pName, String q,boolean checked,String key){
        this.productname = pName;
        this.quantity = q;
        this.checked=checked;
        this.key=key;
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

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
