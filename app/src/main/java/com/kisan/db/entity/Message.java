package com.kisan.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "messages",foreignKeys = {
        @ForeignKey(
                entity = Contact.class,
                parentColumns = "id",
                childColumns = "contactId")})
public class Message {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int contactId;
    public Date date;
    public String text;
}
