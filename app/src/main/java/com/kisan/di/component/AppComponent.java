package com.kisan.di.component;

import android.app.Application;

import com.kisan.App;
import com.kisan.di.module.ActivityModule;
import com.kisan.di.module.AppModule;
import com.kisan.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class, ActivityModule.class, AppModule.class, FragmentModule.class})
public interface AppComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
