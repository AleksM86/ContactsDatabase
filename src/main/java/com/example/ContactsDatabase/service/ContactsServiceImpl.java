package com.example.ContactsDatabase.service;

import com.example.ContactsDatabase.dto.Contact;
import com.example.ContactsDatabase.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;

    @Override
    public List<Contact> findAll() {
        return contactsRepository.findAll();
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactsRepository.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return contactsRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return contactsRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactsRepository.deleteById(id);
    }
}
