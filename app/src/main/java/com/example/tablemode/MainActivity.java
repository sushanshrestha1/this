package com.example.tablemode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // starting background service for android>= android O
                    Log.e("state","foreground");
                    startForegroundService(new Intent(MainActivity.this,motiondetect.class));
                    //service started
                }
                else {
                    Log.e("state","background");
                    // starting background service for android<= android O
                    startService(new Intent(MainActivity.this,motiondetect.class));
                    // service started
                }

            }
        });


    }
}