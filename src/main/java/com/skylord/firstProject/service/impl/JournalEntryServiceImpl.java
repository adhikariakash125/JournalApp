package com.skylord.firstProject.service.impl;

import com.skylord.firstProject.entity.JournalEntry;
import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.repository.JournalEntryRepo;
import com.skylord.firstProject.service.JournalEntryService;
import com.skylord.firstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.getUserByUserName(userName);
            JournalEntry entry = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(entry);
            userService.saveUser(user);
            return entry;
        } catch (Exception exception) {
            throw new RuntimeException("Journal entry could not be create. Rolling back transactions");
        }
    }

    @Override
    public List<JournalEntry> getAllJournalEntry(String userName) {
        try {
            User user = userService.getUserByUserName(userName);
            return user.getJournalEntries();
        } catch (Exception exception) {
            throw new RuntimeException("No user found");
        }
    }

    @Override
    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Override
    @Transactional
    public boolean deleteJournalById(ObjectId id, String userName) {
        boolean isRemoved = false;
        try {
            User user = userService.getUserByUserName(userName);
            isRemoved = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (isRemoved) {
                journalEntryRepo.deleteById(id);
                userService.saveUser(user);
                return isRemoved;
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the id");
        }
        return isRemoved;
    }

    @Override
    public JournalEntry saveJournalEntry(JournalEntry entry) {
        return journalEntryRepo.save(entry);
    }

    @Override
    public ResponseEntity<JournalEntry> updateJournalEntry(JournalEntry journalEntry, ObjectId id, String userName) {
        User user = userService.getUserByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).toList();
        if (journalEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        JournalEntry entry = journalEntries.getFirst();
        entry.setContent(!journalEntry.getContent().isEmpty() ? journalEntry.getContent() : entry.getContent());
        entry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : entry.getTitle());
        JournalEntry savedJournalEntry = saveJournalEntry(entry);
        return new ResponseEntity<>(savedJournalEntry, HttpStatus.OK);
    }
}
