package com.example.timedeal.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String homeController() {

        return "Hello, This is Branch 2";
    }
}
