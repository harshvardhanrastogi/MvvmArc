package com.kisan.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kisan.di.key.ViewModelKey;
import com.kisan.view_model.ContactListViewModel;
import com.kisan.view_model.FactoryViewModel;
import com.kisan.view_model.MessageListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel.class)
    abstract ViewModel bindContactViewModel(ContactListViewModel contactListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MessageListViewModel.class)
    abstract ViewModel bindMessageListViewModel(MessageListViewModel messageListViewModel);
}
