package com.skylord.firstProject.controller;

import com.skylord.firstProject.entity.JournalEntry;
import com.skylord.firstProject.entity.User;
import com.skylord.firstProject.service.JournalEntryService;
import com.skylord.firstProject.service.UserService;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all/{userName}")
    public ResponseEntity<List<JournalEntry>> getJournalsByUser(@PathVariable @NonNull String userName) {
        try {
            List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntry(userName);
            if (journalEntries != null && !journalEntries.isEmpty()) {
                return new ResponseEntity<>(journalEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            System.out.println("No user found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry,
                                                    @PathVariable @NonNull String userName) {
        try {
            JournalEntry entry = journalEntryService.saveJournalEntry(journalEntry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(id);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName,
                                                    @PathVariable ObjectId id) {
        journalEntryService.deleteJournalById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@RequestBody JournalEntry journalEntry,
                                                               @PathVariable String userName,
                                                               @PathVariable ObjectId id) {
        return journalEntryService.updateJournalEntry(journalEntry, id);
    }

}
