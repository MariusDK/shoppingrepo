package com.example.marius.shoppingapp.services;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.example.marius.shoppingapp.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FriendRequestService extends FirebaseMessagingService {
    private static final String TAG = "FR Service";
    public FriendRequestService()
    {}
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size()>0)
        {
            Log.d(TAG,"Message data: "+ remoteMessage.getData());
        }
        if (remoteMessage.getNotification()!=null)
        {createNotification(remoteMessage.getNotification().getBody());}
    }
    public void createNotification(String body)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //s este noul token de inregistrare
    }
}

