package com.example.callthedispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                permissionList,
                PERMISSION_REQUEST_CODE);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            new SetCommand(this, "",0).execute();
        }
        setTitle("Клик звонок");
        llMain = findViewById(R.id.llMain);
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetCommand(MainActivity.this, "",0).execute();
            }
        });
    }
    LinearLayout llMain;
    private static final int REQUEST_READ_CONTACTS = 0;
    int PERMISSION_REQUEST_CODE = 1;
    String[] permissionList = new String[]{
            Manifest.permission.CALL_PHONE,
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateAutoComplete();
                ActivityCompat.requestPermissions(this,
                        permissionList,
                        PERMISSION_REQUEST_CODE);
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE ) {

            if (requestCode == PERMISSION_REQUEST_CODE ) {

            }
        }
    }
}