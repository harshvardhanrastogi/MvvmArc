package com.kisan.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.kisan.db.entity.Contact;
import com.kisan.db.entity.Message;
import com.kisan.repositories.ContactRepository;
import com.kisan.repositories.MessageRepository;
import com.kisan.ui.MessageListAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class MessageListViewModel extends ViewModel {

    private MediatorLiveData<List<Message>> messages;
    private LiveData<List<Message>> _messages;
    private MessageListAdapter adapter;

    @Inject
    public MessageListViewModel(MessageRepository messageRepo,
                                ContactRepository contactRepo,
                                ExecutorService executor) {
        _messages = messageRepo.getMessages();
        messages = new MediatorLiveData<>();
        messages.addSource(_messages, messages1 -> {
            if (messages1 != null) {
                messages.postValue(messages1);
            }
        });
        this.adapter = new MessageListAdapter(executor, contactRepo);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public MessageListAdapter getAdapter() {
        return adapter;
    }
}
