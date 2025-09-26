package com.parkinglot.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this as a REST API controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Parking Lot Reservation System is running!";
    }
}
