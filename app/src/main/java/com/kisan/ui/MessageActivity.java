package com.kisan.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kisan.R;
import com.kisan.databinding.ActivityMessageBinding;
import com.kisan.db.entity.Contact;
import com.kisan.ui.fragment.MessageFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MessageActivity extends AppCompatActivity implements HasAndroidInjector {

    public static final String TAG = "MessageActivity";
    public static final String EXTRA_CONTACT = "extra_contact";

    private ActivityMessageBinding binding;

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        binding.setLifecycleOwner(this);
        Contact contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contact != null) {
            binding.setContact(contact);
        }
        binding.setListener(onSendMessageClickListener);
        configureDagger();
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }

    public void openSendMessageFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, MessageFragment
                        .newInstance(getIntent()
                                .getParcelableExtra(EXTRA_CONTACT)))
                .addToBackStack(MessageFragment.TAG)
                .commit();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    private View.OnClickListener onSendMessageClickListener = v -> openSendMessageFragment();
}
