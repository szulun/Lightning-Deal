package com.yclin_shopping.lightning_deal_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    
    @RequestMapping
    public String hello() {
        return "Hello, World!";
    }
}
