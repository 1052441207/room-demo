package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RoomDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent[] intents = new Intent[2];
                Intent intent = new Intent(MainActivity.this,RoomDemoActivity.class);
                intents[0] = intent;

                try {
                    startActivities(intents);
                }catch (Exception e){
                    Intent newInt = new Intent(MainActivity.this,RoomDemoActivity.class);
                    startActivity(newInt);
                }

            }
        });
    }
}
