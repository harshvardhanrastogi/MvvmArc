package com.kisan.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kisan.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAll(List<Contact> contacts);

    @Query("select * from contacts order by first_name asc")
    LiveData<List<Contact>> getAll();

    @Query("select * from contacts where id = :id")
    Contact getContact(int id);
}
