package com.example.demoprojectapi.controllers;

import com.example.demoprojectapi.Constant;
import com.example.demoprojectapi.models.User;
import com.example.demoprojectapi.services.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> resgisterUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        System.out.println(userMap);
        System.out.println(firstName);
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userServices.registerUser(firstName, lastName, email, password);
        Map<String, String> map = new HashMap<>();


        map.put("message", "register successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    ;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userServices.validateUser(email, password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "login successfully");
        Map<String, String > token = generateJWT(user);
        map.putAll(token);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    private Map<String, String> generateJWT(User user) {
        long timeStamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constant.API_SECRET_KEY)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + Constant.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String > map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
