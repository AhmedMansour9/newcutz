package com.cairocart.Notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LifecycleRegistry;
import androidx.preference.PreferenceManager;

import com.cairocart.Activities.TabsLayout;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title,message,address,time,Day;
    MyNotification mNotificationManager;
    Intent intent;
    Context context;
    SharedPreferences DataSaver ;
    private LifecycleRegistry lifecycleRegistry;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context=getApplicationContext();
        DataSaver= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        EventBus.getDefault().postSticky(new MessageEvent("notify"));
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//            getNewNotification();
            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject json = new JSONObject(params);
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            String status = NetworkUtil.getConnectivityStatusString(context);
            Log.e("Receiver ", "" + status);
            if (status.equals("Not connected to Internet")) {
                Log.e("Receiver ", "not connction");// your code when internet lost
            } else {
                Log.e("Receiver ", "connected to internet");//your code when internet connection come back
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        mNotificationManager = new MyNotification(getApplicationContext());
        intent = new Intent(getApplicationContext(), TabsLayout.class);
        intent.putExtra("notification","notification");
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
//            JSONObject data = json.getJSONObject("data");

            title = json.getString("title");
            message = json.getString("body");
//            if(address.equals("null")) {
            mNotificationManager.showSmallNotificati(title, message, intent);

//            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }



}
