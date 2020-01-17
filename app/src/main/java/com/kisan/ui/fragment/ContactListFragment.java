package com.kisan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.kisan.R;
import com.kisan.databinding.FragmentContactListBinding;
import com.kisan.ui.MessageActivity;
import com.kisan.view_model.ContactListViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.kisan.ui.MessageActivity.EXTRA_CONTACT;

public class ContactListFragment extends DaggerFragment {

    public static final String TAG = "ContactListFragment";
    private FragmentContactListBinding binding;
    private ContactListViewModel viewModel;

    @Inject
    ViewModelProvider.Factory factory;
    public static ContactListFragment newInstance() {
        Bundle args = new Bundle();
        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this, factory).get(ContactListViewModel.class);
        viewModel.getClickedContact().observe(this, contact -> {
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra(EXTRA_CONTACT, contact);
            startActivity(intent);
        });
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

}
