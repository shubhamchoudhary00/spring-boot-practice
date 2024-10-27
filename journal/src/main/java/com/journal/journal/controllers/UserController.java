package com.journal.journal.controllers;

import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.services.JournalEntryService;
import com.journal.journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;




@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;








    @PutMapping()
    public ResponseEntity<User> updateById(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userdb=userService.findByUsername(username);
        if(userdb!=null){
            userdb.setUsername(user.getUsername());
            userdb.setPassword(user.getPassword());
            userService.saveEntry(userdb);
            return new ResponseEntity<>(userdb,HttpStatus.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       userService.deleteByUsername(authentication.getName());
       return new ResponseEntity<>(true,HttpStatus.valueOf(200));

    }

}
