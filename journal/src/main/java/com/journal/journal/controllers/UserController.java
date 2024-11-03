package com.journal.journal.controllers;

import com.journal.journal.api.response.WeatherApiResponse;
import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.services.JournalEntryService;
import com.journal.journal.services.UserService;
import com.journal.journal.services.WeatherService;
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


    @Autowired
    private WeatherService weatherService;





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

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherApiResponse weatherApiResponse= weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherApiResponse!=null){
            greeting=" , weather feels like "+weatherApiResponse.getCurrent().getFeelslikeC();
        }
        return new ResponseEntity<>("Hi"+authentication.getName()+greeting,HttpStatus.valueOf(200));
    }

}
