package com.kisan.binding;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kisan.db.entity.Contact;
import com.kisan.db.entity.Message;
import com.kisan.ui.ContactListAdapter;
import com.kisan.ui.MessageListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdapterUtil {

    @BindingAdapter({"app:contactListAdapter", "app:data"})
    public static void setContactList(RecyclerView recyclerView, ContactListAdapter adapter, List<Contact> contacts) {
        if (adapter != null && contacts != null) {
            recyclerView.setAdapter(adapter);
            adapter.update(contacts);
        }
    }

    @BindingAdapter("divider")
    public static void addDivider(RecyclerView recyclerView, int useless) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @BindingAdapter({"app:messageListAdapter", "app:messages"})
    public static void setMessageList(RecyclerView recyclerView, MessageListAdapter adapter, List<Message> messages) {
        recyclerView.setAdapter(adapter);
        adapter.update(messages);
    }

    @BindingAdapter("otp")
    public static void setOtp(TextView textView, String text) {
        Pattern pattern = Pattern.compile("[0-9]{6}");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String otp = "OTP: " + text.substring(matcher.start(), matcher.end());
            SpannableStringBuilder builder = new SpannableStringBuilder(otp);
            builder.setSpan(new StyleSpan(Typeface.BOLD), 4, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);
        }
    }

    @BindingAdapter("date")
    public static void setDate(TextView textView, Date date) {
        String format = "hh:mm dd-MM-yy";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        textView.setText(dateFormat.format(date));
    }

    @BindingAdapter("error")
    public static void setError(TextInputEditText editText, String error) {
        editText.setError(error);
    }

}
