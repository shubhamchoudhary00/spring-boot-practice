package com.journal.journal.controllers;

import com.journal.journal.entity.User;
import com.journal.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public  String healthCheck(){
        return  "Ok";
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Public endpoint accessible");
    }


    @PostMapping("/create-user")
    public ResponseEntity<?> createEntry(@RequestBody User user){
        try{

            userService.saveNewUser(user);
            return new ResponseEntity<>(true, HttpStatus.valueOf(201));
        }catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.valueOf(400));
        }

    }
}
