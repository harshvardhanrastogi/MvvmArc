package com.kisan.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kisan.ui.ContactListAdapter;
import com.kisan.db.entity.Contact;
import com.kisan.repositories.ContactRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ContactListViewModel extends ViewModel implements ContactListAdapter.OnContactClickListener {

    public static final String TAG = "ContactListViewModel";
    private ContactRepository contactRepository;
    private MediatorLiveData<List<Contact>> contacts;
    private ContactListAdapter adapter;
    private MutableLiveData<Contact> clickedItem;

    @Inject
    public ContactListViewModel(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        adapter = new ContactListAdapter(this);
        contacts = new MediatorLiveData<>();
        LiveData<List<Contact>> contactLiveData = getContactLiveData();
        this.contacts.addSource(contactLiveData, contacts1 -> {
            if(contacts1 != null) {
                contacts.postValue(contacts1);
            }
        });

        clickedItem = new MutableLiveData<>();
    }

    private LiveData<List<Contact>> getContactLiveData() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            Callable<LiveData<List<Contact>>> callable = () -> contactRepository.getContacts();
            return service.submit(callable).get();
        } catch (Exception e) {
            e.printStackTrace();
            return AbsentLiveData.create();
        }
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public ContactListAdapter getAdapter() {
        return adapter;
    }

    public MutableLiveData<Contact> getClickedContact() {
        return clickedItem;
    }

    @Override
    public void onClick(Contact contact) {
        clickedItem.postValue(contact);
    }
}
