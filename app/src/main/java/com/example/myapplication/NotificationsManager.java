package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class NotificationsManager {

    private Context context;
    private NotificationManager notificationManager;
    private Notification helloNotification;
    private Notification goodbyeNotification;
    private int notificationId = 1;
    private DatabaseReference retreff;
    private String Shop = "";
    private String msg = "";





    public NotificationsManager(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //this.helloNotification = buildNotification(getShop(),getMsg());
        //this.goodbyeNotification = buildNotification(getShop(),"سررنا بزيارتك ! إلى اللقاء ..");

    }//end constructor


    private Notification buildNotification(String title, String text) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        return new NotificationCompat.Builder(context, "content_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

    }//end buildNotification

    public void startMonitoring() {
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(context, ((NotificationApplication) context).cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("al-fahad-1418-hotmail-com--fu0")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {

                        if (proximityContext.getAttachments().get("waad").equals("ice")){
                         getNotificationMsg("ice");
                        notificationManager.notify(notificationId, buildNotification(getShop(), getMsg()));
                    }else if(proximityContext.getAttachments().get("waad").equals("coconut")) {
                            getNotificationMsg("coconut");
                            notificationManager.notify(notificationId, buildNotification(getShop(), getMsg()));
                        }else{
                        getNotificationMsg("blueberry");
                        notificationManager.notify(notificationId, buildNotification(getShop(),getMsg()));}


                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(notificationId, buildNotification(getShop(),"سررنا بزيارتك ! إلى اللقاء .."));
                        return null;
                    }
                })

                .build();

        proximityObserver.startObserving(zone);

    }//end startMonitoring


    public void setShop(String shop) {
        Shop = shop;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShop() {
        return Shop;
    }

    public String getMsg() {
        return msg;
    }

    public void getNotificationMsg(final String BeaconName){

        retreff = FirebaseDatabase.getInstance().getReference("Advertisment Information");
        retreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {



                        advertismentInfo AdvInfo = ds.getValue(advertismentInfo.class);


                        if(AdvInfo.getNameOfAdvertisment().equals(BeaconName)) {

                            setShop(AdvInfo.getShopName());
                            setMsg(AdvInfo.getDescription());
                            break;
                        }





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
}
