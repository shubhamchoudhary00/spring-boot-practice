package com.journal.journal.controllers;

import com.journal.journal.entity.User;
import com.journal.journal.services.UserDetailServiceImpl;
import com.journal.journal.services.UserService;
import com.journal.journal.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/health-check")
    public  String healthCheck(){
        return  "Ok";
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Public endpoint accessible");
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){
        try{

            userService.saveNewUser(user);
            return new ResponseEntity<>(true, HttpStatus.valueOf(201));
        }catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.valueOf(400));
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails=userDetailService.loadUserByUsername(user.getUsername());
            String jwtToken= jwtUtil.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwtToken,HttpStatus.valueOf(200));
        }catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.valueOf(400));
        }

    }
}
