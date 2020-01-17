package com.kisan.view_model;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import com.google.gson.Gson;
import com.kisan.App;
import com.kisan.BR;
import com.kisan.db.entity.Message;
import com.kisan.repositories.MessageRepository;
import com.kisan.rest.ErrorBody;
import com.kisan.rest.MessageService;
import com.kisan.rest.SmsResponse;
import com.kisan.ui.fragment.MessageFragment;
import com.kisan.util.AppUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import static com.kisan.BR.*;
import static com.kisan.BR.*;

public class MessageViewModel extends BaseObservable {

    public static final String PATTERN_DATE = "EEE, dd MMM yyyy HH:mm:ss Z";
    public static final String ERROR_UNKNOWN = "Error sending message : error unknown.";
    public static final String ERROR_SENDING_MESSAGE = "Error sending message :";
    private String text;
    private boolean enableSend;
    private int contactId;
    private boolean errorEnabled;
    private String errorText;
    public static final String TAG = "MessageViewModel";

    private final MessageRepository messageRepository;

    private MessageFragment.MessageSendListener listener;


    private Executor executor;
    private MessageService messageService;

    @Inject
    public MessageViewModel(MessageRepository messageRepository, Executor executor, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.executor = executor;
        this.messageService = messageService;
    }

    public void setText(String text) {
        this.text = text;
        Log.d(TAG, text);
        notifyPropertyChanged(com.kisan.BR.text);
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void sendMessage() {
        try {
            validateInput();
            setErrorEnabled(false);
            setEnableSend(false);
            setErrorText(null);
            sendSms();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            showToast(e.getMessage());
            setErrorEnabled(true);
            setErrorText(e.getMessage());
        }
    }

    private void validateInput() throws IllegalArgumentException {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Message text doesn't have OTP key or 6-digit OTP");
        }
        Pattern pat = Pattern.compile("([0-9]{6})|\bOTP\b");
        Matcher matcher = pat.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Message text doesn't have OTP key or 6-digit OTP");
        }
    }

    private void saveMessage(SmsResponse body) {
        Message message = new Message();
        message.contactId = contactId;
        try {
            message.date = AppUtils.toDate(body.getDateCreated(), PATTERN_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        message.text = body.getBody();
        executor.execute(() -> messageRepository.saveMessage(message));
    }

    private void sendSms() {
        Call<SmsResponse> smsResp = messageService.sendSms("+15053696795", "+917998053670", text);
        smsResp.enqueue(new Callback<SmsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SmsResponse> call, @NonNull Response<SmsResponse> response) {
                SmsResponse body = response.body();
                setEnableSend(true);
                if (body == null || response.code() == 204) {
                    try {
                        String errResp = response.errorBody().string();
                        if (!TextUtils.isEmpty(errResp)) {
                            ErrorBody errBody = new Gson().fromJson(errResp, ErrorBody.class);
                            showToast(ERROR_SENDING_MESSAGE +
                                    " errorCode=" + errBody.getCode() + ", " +
                                    "errorMessage=" + errBody.getMessage());
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        showToast(ERROR_UNKNOWN);
                    }
                    showToast(ERROR_UNKNOWN);
                    return;
                }
                saveMessage(body);
                showToast("Message sent successfully.");
                Objects.requireNonNull(listener).onSent();
            }

            @Override
            public void onFailure(@NonNull Call<SmsResponse> call, @NonNull Throwable t) {
                setEnableSend(true);
                showToast(ERROR_SENDING_MESSAGE + t.getMessage());
            }
        });
    }

    private void showToast(String s) {
        Toast.makeText(App.context, s,
                Toast.LENGTH_LONG).show();
    }

    public void setEnableSend(boolean enableSend) {
        this.enableSend = enableSend;
        notifyPropertyChanged(com.kisan.BR.enableSend);
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setClickListener(MessageFragment.MessageSendListener listener) {
        this.listener = listener;
    }

    @Bindable
    public boolean isEnableSend() {
        return enableSend;
    }

    @Bindable
    public boolean isErrorEnabled() {
        return errorEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
        notifyPropertyChanged(com.kisan.BR.errorEnabled);
    }

    @Bindable
    public String getErrorText() {
        return errorText;
    }

    private void setErrorText(String errorText) {
        this.errorText = errorText;
        notifyPropertyChanged(com.kisan.BR.errorText);
    }
}
