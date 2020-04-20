package com.example.myapplication;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class NotificationApplication extends Application {

    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("al-fahad-1418-hotmail-com--65s", "13b04f564f9473450fb34ccbcdb36573");
    private NotificationsManager notificationsManager;


    public void enableBeaconNotifications() {

        notificationsManager = new NotificationsManager(this);
        notificationsManager.startMonitoring();

    }//end enableBeaconNotifications

}//end class
