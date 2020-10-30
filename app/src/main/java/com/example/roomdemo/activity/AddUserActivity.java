package com.example.roomdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class AddUserActivity extends AppCompatActivity {

    EditText etName;
    EditText etAge;

    private AppDatabase appDatabase;

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initView();
        initData();
    }

    private void initData() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-event").build();
        refresh();

    }


    private void refresh(){
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

    private void initView() {

        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 50;
            }
        });
        userAdapter = new UserAdapter(this);
        recyclerView.setAdapter(userAdapter);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.name = etName.getText().toString();
                user.age = Integer.parseInt(etAge.getText().toString());

                appDatabase.userDao().insert(user)
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<long[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(long[] longs) {
                        Log.e("main","ok");
                        refresh();
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
        Toast.makeText(AddUserActivity.this,"添加失败",Toast.LENGTH_LONG).show();
    }


    public static void launch(Context context){
        Intent intent = new Intent(context,AddUserActivity.class);
        context.startActivity(intent);
    }
}
