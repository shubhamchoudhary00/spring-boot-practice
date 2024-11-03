package com.journal.journal.controllers;

import com.journal.journal.cache.AppCache;
import com.journal.journal.entity.User;
import com.journal.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> users=userService.getAll();
        if(users!=null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/clear-app-cache")
    public void clearCache(){
        appCache.init();
    }
}
