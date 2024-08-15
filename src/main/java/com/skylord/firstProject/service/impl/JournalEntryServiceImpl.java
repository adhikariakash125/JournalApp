package com.skylord.firstProject.service.impl;

import com.skylord.firstProject.entity.JournalEntry;
import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.repository.JournalEntryRepo;
import com.skylord.firstProject.service.JournalEntryService;
import com.skylord.firstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    public JournalEntry saveJournalEntry(JournalEntry journalEntry,String userName){
        User user = userService.getUserByUserName(userName);
        JournalEntry entry = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(entry);
        userService.saveUser(user);
        return entry;
    }

    @Override
    public List<JournalEntry> getAllJournalEntry() {
        return journalEntryRepo.findAll();
    }

    @Override
    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Override
    public void deleteJournalById(ObjectId id, String userName) {
        User user = userService.getUserByUserName(userName);
        journalEntryRepo.deleteById(id);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
    }

    @Override
    public JournalEntry saveJournalEntry(JournalEntry entry) {
        return journalEntryRepo.save(entry);
    }
}
