package com.krypto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String Home() {
        return "Welcome to Krypto";
    }

    @GetMapping("/secure")
    public String  secure(){
    return "SECURE";
}

}


