package com.example.marius.shoppingapp.broadcastReciver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.providers.FriendsProvider;

public class AddFriendReciver extends BroadcastReceiver {
    private FriendsProvider friendsProvider;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals("YES_ACTION"))
        {
            friendsProvider = new FriendsProvider();
            String email = intent.getStringExtra("email");
            friendsProvider.addFriend(email);
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(R.id.addFriendNotificationId);
        }
        if (action.equals("NO_ACTION"))
        {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(R.id.addFriendNotificationId);
        }
    }
}
