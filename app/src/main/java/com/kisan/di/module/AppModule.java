package com.kisan.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.google.gson.Gson;
import com.kisan.db.KisanDatabase;
import com.kisan.db.dao.ContactDao;
import com.kisan.db.dao.MessageDao;
import com.kisan.repositories.ContactRepository;
import com.kisan.repositories.MessageRepository;
import com.kisan.rest.MessageService;
import com.kisan.rest.ServiceGenerator;
import com.kisan.view_model.MessageViewModel;
import com.kisan.view_model.MessageListViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    KisanDatabase provideDatabase(Application application) {
        return Room
                .databaseBuilder(application, KisanDatabase.class, "KisanDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    ContactDao provideContactDao(KisanDatabase db) {
        return db.contactDao();
    }

    @Provides
    @Singleton
    MessageDao provideMessageDao(KisanDatabase db) {
        return db.messageDao();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    ExecutorService provideExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    ContactRepository provideContactRepository(ContactDao contactDao) {
        return new ContactRepository(contactDao);
    }

    @Provides
    @Singleton
    MessageRepository provideMessageRepository(MessageDao messageDao) {
        return new MessageRepository(messageDao);
    }

    @Provides
    MessageViewModel provideMessageViewModel(MessageRepository messageRepository, Executor executor, MessageService messageService) {
        return new MessageViewModel(messageRepository, executor, messageService);
    }

    @Provides
    MessageListViewModel provideMessageListViewModel(MessageRepository msgRepo, ContactRepository contactRepo, ExecutorService exeSer) {
        return new MessageListViewModel(msgRepo, contactRepo, exeSer);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    SharedPreferences provideSharedPref(Application application) {
        return application.getSharedPreferences("kisan_pref", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    MessageService provideMessageService() {
        return ServiceGenerator.createService(MessageService.class,
                "sid", "authtoken");
    }
}
