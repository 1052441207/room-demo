package com.example.roomdemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdemo.dao.EventDao;
import com.example.roomdemo.dao.UserDao;
import com.example.roomdemo.entity.Event;
import com.example.roomdemo.entity.User;

@Database(entities = {User.class, Event.class},version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract UserDao userDao();
}
