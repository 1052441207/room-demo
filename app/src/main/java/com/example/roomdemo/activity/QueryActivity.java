package com.example.roomdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.roomdemo.R;
import com.example.roomdemo.database.AppDatabase;
import com.example.roomdemo.entity.EventWithUser;
import com.example.roomdemo.entity.UserEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QueryActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private TextView tvContent;

    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
        initData();
    }

    private void initData() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-event").build();
        gson = new Gson();
    }

    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_get_all_user_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.userDao()
                        .getAllUserEvents()
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<UserEvents>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<UserEvents> userEvents) {
                        tvContent.setText(jsonFormatter(gson.toJson(userEvents)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        alert();
                    }
                });
            }
        });

        findViewById(R.id.btn_get_all_events_with_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatabase.eventDao()
                        .getAllEventsWithUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<EventWithUser>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<EventWithUser> eventWithUsers) {
                                tvContent.setText(jsonFormatter(gson.toJson(eventWithUsers)));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

            }
        });
    }

    public static String jsonFormatter(String uglyJSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }


    private void alert(){
        Toast.makeText(QueryActivity.this,"查询失败",Toast.LENGTH_LONG).show();
    }


    public static void launch(Context context){
        Intent intent = new Intent(context,QueryActivity.class);
        context.startActivity(intent);
    }
}
