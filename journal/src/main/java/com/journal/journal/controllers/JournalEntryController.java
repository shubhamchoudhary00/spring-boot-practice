package com.journal.journal.controllers;

import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.services.JournalEntryService;
import com.journal.journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUsername(username);
     List<JournalEntity> all= user.getJournalEntries();
     if(all!=null && !all.isEmpty()){
         return new ResponseEntity<>(all, HttpStatusCode.valueOf(200));
     }else{
         return new ResponseEntity<>(HttpStatus.valueOf(404));
     }
    }
    @PostMapping()
    public  ResponseEntity<?> createEntry(@RequestBody JournalEntity entry){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry,username);
            return new ResponseEntity<>(true,HttpStatus.valueOf(201));
        }catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.valueOf(400));
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getById(@PathVariable String myId){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            User user=userService.findByUsername(username);
            ObjectId objectId = new ObjectId(myId);
            List<JournalEntity> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                JournalEntity entry= journalEntryService.getById(objectId).orElse(null);
                if(entry!=null){
                    return  new ResponseEntity<>(entry,HttpStatus.valueOf(200));
                }

            }
            return  null;

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.valueOf(404));
        }


    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            ObjectId objectId = new ObjectId(myId);
            journalEntryService.deleteById(objectId,username);
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }

    }
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable String myId, @RequestBody JournalEntity entry) {
        try {
            // Retrieve the currently authenticated user's information
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            // Validate ObjectId format
            ObjectId objectId;
            try {
                objectId = new ObjectId(myId);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid ID format
            }

            // Check if the user has the journal entry
            boolean entryExists = user.getJournalEntries().stream()
                    .anyMatch(x -> x.getId().equals(myId));

            if (!entryExists) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User does not have access
            }

            // Fetch the current entry
            JournalEntity currentEntry = journalEntryService.getById(objectId).orElse(null);

            if (currentEntry != null) {
                // Update fields only if they are provided in the request body
                if (entry.getTitle() != null && !entry.getTitle().isEmpty()) {
                    currentEntry.setTitle(entry.getTitle());
                }
                if (entry.getContent() != null && !entry.getContent().isEmpty()) {
                    currentEntry.setContent(entry.getContent());
                }

                // Save the updated entry
                journalEntryService.saveEntry(currentEntry);
                return new ResponseEntity<>(currentEntry, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Entry not found
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // General error handling
        }
    }

}
