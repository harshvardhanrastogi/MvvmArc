package com.kisan.di.module;

import androidx.fragment.app.Fragment;

import com.kisan.ui.fragment.ContactListFragment;
import com.kisan.ui.fragment.MessageFragment;
import com.kisan.ui.fragment.MessageListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule extends Fragment {
    @ContributesAndroidInjector
    abstract MessageFragment provideMessageFragment();

    @ContributesAndroidInjector
    abstract ContactListFragment provideContactListFragment();

    @ContributesAndroidInjector
    abstract MessageListFragment provideMEssageListFragment();
}
