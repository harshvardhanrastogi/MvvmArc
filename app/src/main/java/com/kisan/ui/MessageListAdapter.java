package com.kisan.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kisan.R;
import com.kisan.databinding.ItemMessageListBinding;
import com.kisan.db.entity.Contact;
import com.kisan.db.entity.Message;
import com.kisan.repositories.ContactRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<Message> messages;
    private ExecutorService executorService;
    private ContactRepository contactRepo;

    public MessageListAdapter(ExecutorService executorService, ContactRepository contactRepo) {
        this.executorService = executorService;
        this.contactRepo = contactRepo;
    }

    public void update(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageListBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                        .from(parent
                                .getContext()), R.layout.item_message_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messages.get(position));
        Contact contact = getContact(messages.get(position).contactId);
        holder.bind(Objects.requireNonNull(contact));
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMessageListBinding binding;

        ViewHolder(@NonNull ItemMessageListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            binding.setMessage(message);
        }

        void bind(Contact contact) {
            binding.setContact(contact);
        }

        void unbind() {
            binding.unbind();
        }
    }

    private Contact getContact(int id) {
        try {
            return executorService.submit(() -> contactRepo.getContact(id)).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
