package com.example.smsforwarder;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // অ্যাপ ওপেন করলে স্ক্রিনে এই লেখাটি দেখাবে
        TextView tv = new TextView(this);
        tv.setText("bKash SMS Forwarder is Running...\n\nPlease grant SMS permission if asked.");
        tv.setPadding(50, 50, 50, 50);
        tv.setTextSize(18f);
        setContentView(tv);

        // পারমিশন চাইবে
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            } else {
                Toast.makeText(this, "Permission granted! App is ready.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "App is ready.", Toast.LENGTH_LONG).show();
        }
    }
}
