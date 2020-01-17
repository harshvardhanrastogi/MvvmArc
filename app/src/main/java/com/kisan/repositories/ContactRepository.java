package com.kisan.repositories;

import androidx.lifecycle.LiveData;

import com.kisan.db.dao.ContactDao;
import com.kisan.db.entity.Contact;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactRepository {

    private final ContactDao contactDao;

    @Inject
    public ContactRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public LiveData<List<Contact>> getContacts() {
        return contactDao.getAll();
    }

    public Contact getContact(int id) {
        return contactDao.getContact(id);
    }

    public void saveAll(List<Contact> contacts) {
        contactDao.addAll(contacts);
    }
}
