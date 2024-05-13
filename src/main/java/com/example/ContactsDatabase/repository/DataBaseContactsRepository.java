package com.example.ContactsDatabase.repository;

import com.example.ContactsDatabase.dto.Contact;
import com.example.ContactsDatabase.exception.ContactNotFoundException;
import com.example.ContactsDatabase.repository.mapper.ContactsRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataBaseContactsRepository implements ContactsRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        log.debug("Calling findAll in DataBaseContactsRepository");
        String query = "SELECT * FROM contacts";
        return  jdbcTemplate.query(query,new ContactsRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling findById in DataBaseContactsRepository, id = " + id);
        String query = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        query,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactsRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling save in DataBaseContactsRepository, contact = " + contact);
        contact.setId(System.currentTimeMillis());
        String query = "INSERT INTO contacts (id, firstName, lastName, email, phone) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, contact.getId(), contact.getFirstName(), contact.getLastName(),
                contact.getEmail(), contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling update in DataBaseContactsRepository, contact = " + contact);
        Contact existedContact = findById(contact.getId()).orElse(null);
        String query = "UPDATE contacts SET  firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
        if (existedContact != null){
            jdbcTemplate.update(query, contact.getFirstName(), contact.getLastName(),
                    contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }
        throw new ContactNotFoundException("Contact not found");
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling deleteById in DataBaseContactsRepository, id = " + id);
        String query = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
