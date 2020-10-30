package com.example.roomdemo.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserEvents {
    @Embedded
    public User user;
    @Relation(parentColumn = "user_id",entityColumn = "event_user_id")
    public List<Event> events;
}
