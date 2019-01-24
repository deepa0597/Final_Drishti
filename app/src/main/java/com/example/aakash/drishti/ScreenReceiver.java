package com.example.aakash.drishti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Intent intent1 = new Intent(context, GestureActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}