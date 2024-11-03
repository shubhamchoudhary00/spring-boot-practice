package com.journal.journal.services;

import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;
import com.journal.journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    public void saveEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void saveUser(User user){

        userRepository.save(user);
    }
    public void saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userRepository.save(user);
        }catch (Exception e){
            logger.error("Error occured for {}:",user.getUsername(),e);
            throw new RuntimeException("A run time error");
        }

    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User","Admin"));
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
