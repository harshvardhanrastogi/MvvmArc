package com.kisan.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kisan.db.entity.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("select * from messages ORDER BY date DESC")
    LiveData<List<Message>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Message message);

}
