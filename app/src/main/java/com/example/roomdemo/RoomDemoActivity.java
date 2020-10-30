package com.example.roomdemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.roomdemo.activity.AddEventActivity;
import com.example.roomdemo.activity.AddUserActivity;
import com.example.roomdemo.activity.QueryActivity;
import com.example.roomdemo.database.AppDatabase;

public class RoomDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initView();
        initData();
    }

    private void initData() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-event").build();
    }

    private void initView() {
        findViewById(R.id.btn_insert_event).setOnClickListener(this);
        findViewById(R.id.btn_insert_user).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_insert_event:
                AddEventActivity.launch(RoomDemoActivity.this);
                break;
            case R.id.btn_insert_user:
                AddUserActivity.launch(RoomDemoActivity.this);
                break;
            case R.id.btn_query:
                QueryActivity.launch(this);
                break;

        }
    }
}
