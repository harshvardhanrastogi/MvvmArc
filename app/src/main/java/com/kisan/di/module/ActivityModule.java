package com.kisan.di.module;

import com.kisan.ui.HomeActivity;
import com.kisan.ui.MessageActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = FragmentModule.class)
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MessageActivity contributeMessageActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract HomeActivity contributeHomeActivity();
}
