package com.kisan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.kisan.R;
import com.kisan.databinding.FragmentMessageBinding;
import com.kisan.db.entity.Contact;
import com.kisan.view_model.MessageViewModel;

import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.kisan.ui.MessageActivity.EXTRA_CONTACT;


public class MessageFragment extends DaggerFragment {

    public static final String TAG = "MessageFragment";

    @Inject
    MessageViewModel messageViewModel;

    public static MessageFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CONTACT, contact);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMessageBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_message, container, false);
        binding.setLifecycleOwner(this);

        Contact contact = Objects.requireNonNull(getArguments()).getParcelable(EXTRA_CONTACT);
        String otpText = "Hi. Your OTP is: " + getOTP() + ".";
        messageViewModel.setText(otpText);
        messageViewModel.setEnableSend(true);
        messageViewModel.setContactId(Objects.requireNonNull(contact).id);
        messageViewModel.setClickListener(this::closeActivity);
        messageViewModel.setErrorEnabled(true);
        binding.setMessageViewModel(messageViewModel);

        return binding.getRoot();
    }

    private void closeActivity() {
        Objects.requireNonNull(getActivity()).finish();
    }

    private int getOTP() {
        return new Random().nextInt(999999);
    }

    public interface MessageSendListener {
        void onSent();
    }

}
