package com.kisan.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kisan.db.converter.DateConverter;
import com.kisan.db.dao.ContactDao;
import com.kisan.db.dao.MessageDao;
import com.kisan.db.entity.Contact;
import com.kisan.db.entity.Message;

@Database(version = 1, entities = {Contact.class, Message.class})
@TypeConverters(DateConverter.class)
public abstract class KisanDatabase extends RoomDatabase {
    public static KisanDatabase INSTANCE;

    public abstract ContactDao contactDao();

    public abstract MessageDao messageDao();
}
