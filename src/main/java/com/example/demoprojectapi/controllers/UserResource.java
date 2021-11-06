package com.example.demoprojectapi.controllers;

import com.example.demoprojectapi.models.User;
import com.example.demoprojectapi.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserServices userServices;
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> resgisterUser (@RequestBody Map<String, Object> userMap){
        String firstName = (String) userMap.get("firstName");
        System.out.println(userMap);
        System.out.println(firstName);
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userServices.registerUser(firstName, lastName,email,password);
        Map<String,String> map = new HashMap<>();
        map.put("message", "register successfully");

        return new ResponseEntity<>(map, HttpStatus.OK);
    };

    @PostMapping("/login")
    public ResponseEntity<Map <String, String>> login (@RequestBody Map<String, Object> userMap){
        String email =(String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userServices.validateUser(email, password);
        Map<String,String> map = new HashMap<>();
        map.put("message", "login successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
}
