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
            createNotification(messageBody,messageTitle);
        }
    }
    public void createNotification(String messageBody, String messageTitle)
    {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent yesReceiver = new Intent();
        yesReceiver.putExtra("email",messageBody);
        yesReceiver.setAction("YES_ACTION");
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceiver, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.drawable.ic_check_black_24dp,"Accept",pendingIntentYes);

        Intent noReceiver = new Intent();
        noReceiver.setAction("NO_ACTION");
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(this, 12345, noReceiver, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.drawable.ic_close_black_24dp,"Decline",pendingIntentNo);


        //int notificationId = (int) System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(R.id.addFriendNotificationId,mBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //s este noul token de inregistrare
    }
}

