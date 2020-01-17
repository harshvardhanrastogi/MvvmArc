package com.kisan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.kisan.R;
import com.kisan.databinding.FragmentMessageListBinding;
import com.kisan.ui.MessageListAdapter;
import com.kisan.view_model.MessageListViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MessageListFragment extends DaggerFragment {

    private MessageListViewModel viewModel;

    @Inject
    ViewModelProvider.Factory factory;

    public static MessageListFragment newInstance() {

        Bundle args = new Bundle();

        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMessageListBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_message_list, container, false);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this, factory).get(MessageListViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}

