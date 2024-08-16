package com.skylord.firstProject.service;

import com.skylord.firstProject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {
    JournalEntry saveJournalEntry(JournalEntry journalEntry, String user);

    List<JournalEntry> getAllJournalEntry(String userName);

    Optional<JournalEntry> getJournalEntryById(ObjectId id);

    void deleteJournalById(ObjectId id, String userName);

    JournalEntry saveJournalEntry(JournalEntry entry);

    ResponseEntity<JournalEntry> updateJournalEntry(JournalEntry journalEntry, ObjectId id);
}
