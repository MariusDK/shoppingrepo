package com.example.marius.shoppingapp.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.marius.shoppingapp.R;
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
        {
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            int notificationId = (int) System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId,mBuilder.build());
        }
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

