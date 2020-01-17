package com.kisan.repositories;

import androidx.lifecycle.LiveData;

import com.kisan.db.dao.MessageDao;
import com.kisan.db.entity.Message;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageRepository {
    private final MessageDao messageDao;

    @Inject
    public MessageRepository(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public LiveData<List<Message>> getMessages() {
        return messageDao.getAll();
    }

    public void saveMessage(Message message) {
        messageDao.save(message);
    }
}
