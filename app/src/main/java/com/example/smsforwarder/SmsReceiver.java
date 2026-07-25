package com.example.smsforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String senderNumber = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();

                    // চেক করুন মেসেজটি বিকাশ থেকে এসেছে কিনা
                    if (senderNumber != null && (senderNumber.equalsIgnoreCase("bKash") || senderNumber.equals("16247"))) {
                        sendDataToWebsite(context, senderNumber, messageBody);
                    }
                }
            }
        }
    }

    private void sendDataToWebsite(Context context, String sender, String message) {
        // আপনার ওয়েবসাইটের API লিংক
        String url = "https://abroadmarriagemedia.com/api_receive_sms.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Log.d("SMS_FORWARD", "Success: " + response),
                error -> Log.d("SMS_FORWARD", "Error: " + error.toString())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // PHP ফাইলের সিক্রেট পাসওয়ার্ডের সাথে এটি মিলতে হবে
                params.put("secret", "amar_secret_password_123"); 
                params.put("sender", sender);
                params.put("message", message);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
