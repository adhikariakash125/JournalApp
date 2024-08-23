package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.JournalEntry;
import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.JournalEntryService;
import com.skylord.firstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal/entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getJournalsByUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntry(userName);
            if (journalEntries != null && !journalEntries.isEmpty()) {
                return new ResponseEntity<>(journalEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            System.out.println("No user found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            JournalEntry entry = journalEntryService.saveJournalEntry(journalEntry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        List<JournalEntry> doesJournalExist = user.getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).toList();
        if (doesJournalExist.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(id);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isDeleted = journalEntryService.deleteJournalById(id, userName);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@RequestBody JournalEntry journalEntry,
                                                               @PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalEntryService.updateJournalEntry(journalEntry, id, userName);
    }
}
