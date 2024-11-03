package com.journal.journal.services;

import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(JournalEntity entry,String username){
       try{
           User user=userService.findByUsername(username);
           JournalEntity saved=journalEntryRepository.save(entry);
           user.getJournalEntries().add(saved);
//           user.setUsername(null);
           userService.saveUser(user);
       }catch (Exception e){
           System.out.println(e);
           throw new RuntimeException("An error has occured");
       }

    }
    public void saveEntry(JournalEntity entry){
        journalEntryRepository.save(entry);

    }

    public List<JournalEntity> getAll(){
         return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public  void deleteById(ObjectId id,String username){
        try{
            User user=userService.findByUsername(username);
            boolean removed= user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                journalEntryRepository.deleteById(id);
                userService.saveUser(user);
            }
        }catch (Exception e){
            throw new RuntimeException("An error Occured");
        }



    }

}


// controller --> service --> repository