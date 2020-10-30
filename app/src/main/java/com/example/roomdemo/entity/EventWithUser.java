package com.example.roomdemo.entity;

import androidx.room.Embedded;
import androidx.room.Relation;


public class EventWithUser {
    @Embedded
    public Event event;
    @Relation(parentColumn = "event_user_id",entityColumn = "user_id")
    public User user;
}
