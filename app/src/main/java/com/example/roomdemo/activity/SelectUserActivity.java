package com.example.roomdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.roomdemo.R;
import com.example.roomdemo.adapter.UserAdapter;
import com.example.roomdemo.database.AppDatabase;
import com.example.roomdemo.entity.User;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectUserActivity extends AppCompatActivity {

    public static final int CODE_ADD_USER = 1005;


    private RecyclerView rvUsers;

    private UserAdapter userAdapter;
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        rvUsers =findViewById(R.id.rv_user);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        rvUsers.setAdapter(userAdapter);
        rvUsers.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 50;
            }
        });
        userAdapter.setOnClickItemListener(new UserAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(User user) {
                Intent intent = new Intent();
                intent.putExtra("userId",user.userId);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        initData();
    }

    private void initData() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-event").build();
        appDatabase.userDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<User> users) {
                        userAdapter.setUsers(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("main","ok");
                    }
                });
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,SelectUserActivity.class);
        activity.startActivityForResult(intent,CODE_ADD_USER);
    }
}
