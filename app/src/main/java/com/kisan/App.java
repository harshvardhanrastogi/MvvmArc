package com.kisan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kisan.db.KisanDatabase;
import com.kisan.db.entity.Contact;
import com.kisan.di.component.DaggerAppComponent;
import com.kisan.repositories.ContactRepository;
import com.kisan.util.AppUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import static com.kisan.util.AppUtils.KEY_DB_CREATED;

public class App extends Application implements HasAndroidInjector {
    public static final String TAG = "KisanApp";
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    public static Context context;

    @Inject
    KisanDatabase kisanDb;

    @Inject
    Gson gson;

    @Inject
    ContactRepository contactRepo;

    @Inject
    SharedPreferences sharedPref;

    @Inject
    Executor executor;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        context = getApplicationContext();
        Log.d(TAG, "Db created " + sharedPref.getBoolean(KEY_DB_CREATED, false));
        initDb();

    }

    private void initDb() {
        if (!sharedPref.getBoolean(KEY_DB_CREATED, false)) {
            executor.execute(() -> {
                String strData = AppUtils.readMockDataFromRaw(getResources());
                Type type = new TypeToken<List<Contact>>() {
                }.getType();
                List<Contact> contactList = gson.fromJson(strData, type);
                if (AppUtils.addMockData(contactRepo, contactList)) {
                    sharedPref.edit().putBoolean(KEY_DB_CREATED, true).apply();
                }
            });
        }
    }


    public void initDagger() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }


}
