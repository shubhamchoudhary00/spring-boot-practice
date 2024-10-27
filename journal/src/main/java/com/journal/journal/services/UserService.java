package com.journal.journal.services;

import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;
import com.journal.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void saveUser(User user){

        userRepository.save(user);
    }
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public  void deleteById(ObjectId id){
        userRepository.deleteById(id);
        return;
    }
    public  void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
        return;
    }

    public User findByUsername(String username){
        return  userRepository.findByUsername(username);
    }

}
