package com.example.roomdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.roomdemo.R;
import com.example.roomdemo.database.AppDatabase;
import com.example.roomdemo.entity.Event;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddEventActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etStartTime;
    private EditText etEndTime;
    private long userId = -1;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initView();
        initData();
    }

    private void initData() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-event").build();
    }

    private void initView() {

        etTitle = findViewById(R.id.et_event_title);
        etStartTime = findViewById(R.id.et_event_start_time);
        etEndTime = findViewById(R.id.et_event_end_time);

        findViewById(R.id.btn_select_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectUserActivity.launch(AddEventActivity.this);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Event event = new Event();
                event.title = etTitle.getText().toString();
                event.startTime = Long.parseLong(etStartTime.getText().toString());
                event.endTime = Long.parseLong(etEndTime.getText().toString());
                event.userId = userId;

                appDatabase.eventDao()
                        .insert(event)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<long[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(long[] longs) {
                        Toast.makeText(AddEventActivity.this,"事件添加成功",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        alert();
                    }
                });
            }
        });
    }

    private void alert(){
        Toast.makeText(AddEventActivity.this,"添加失败",Toast.LENGTH_LONG).show();
    }



    public static void launch(Context context){
        Intent intent = new Intent(context,AddEventActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectUserActivity.CODE_ADD_USER
                && resultCode == RESULT_OK){
            userId = data.getLongExtra("userId",userId);
        }
    }
}
