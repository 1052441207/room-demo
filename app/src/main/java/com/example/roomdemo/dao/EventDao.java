package com.example.roomdemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roomdemo.entity.Event;
import com.example.roomdemo.entity.EventWithUser;
import com.example.roomdemo.entity.UserEvents;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EventDao {

    @Insert
    Single<long[]> insert(Event...events);

    @Insert
    void insert(Event event1,Event event2);

    @Insert
    void insert(Event event1, List<Event> events);

    @Update
    void update(Event... events);

    @Delete
    void delete(Event... events);



    @Transaction
    @Query("SELECT * FROM events")
    Single<List<EventWithUser>> getAllEventsWithUser();
}
