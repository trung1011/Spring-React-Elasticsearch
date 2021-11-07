package com.example.demoprojectapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/documents")
public class DocumentResource {
    @GetMapping("")
    public String getAllDocuments(HttpServletRequest request){
        int userId= (Integer) request.getAttribute("userId");
        return "Authe" + userId;
    }
}
