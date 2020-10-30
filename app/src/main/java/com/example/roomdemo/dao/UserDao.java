package com.example.roomdemo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.roomdemo.entity.Event;
import com.example.roomdemo.entity.User;
import com.example.roomdemo.entity.UserEvents;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    Single<List<User>> getAll();


    @Insert
    Single<long[]> insert(User...users);

    @Transaction
    @Query("SELECT * FROM users")
    Single<List<UserEvents>> getAllUserEvents();


}
