package com.kisan.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kisan.R;
import com.kisan.databinding.ItemContactListBinding;
import com.kisan.db.entity.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private OnContactClickListener itemClickListener;

    public ContactListAdapter(OnContactClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void update(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactListBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                        .from(parent
                                .getContext()), R.layout.item_contact_list, parent, false);

        ContactViewHolder holder = new ContactViewHolder(binding);
        View.OnClickListener onClickListener = v -> itemClickListener
                .onClick(contacts.get(holder.getAdapterPosition()));
        binding.getRoot().setOnClickListener(onClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ContactViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        private ItemContactListBinding binding;

        ContactViewHolder(@NonNull ItemContactListBinding contactListBinding) {
            super(contactListBinding.getRoot());
            binding = contactListBinding;
        }

        void bind(Contact contact) {
            binding.setContact(contact);
        }

        void unbind() {
            binding.unbind();
        }
    }

    public interface OnContactClickListener {
        void onClick(Contact contact);
    }
}
