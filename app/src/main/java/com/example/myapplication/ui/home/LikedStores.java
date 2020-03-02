package com.example.myapplication.ui.home;

public class LikedStores  {

    String id,storeId,userId;

    public LikedStores(String id, String storeId, String userId) {
        this.id = id;
        this.storeId = storeId;
        this.userId = userId;
    }

    public LikedStores() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
