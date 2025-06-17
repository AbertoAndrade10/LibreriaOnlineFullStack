package com.porfolio.user_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class Prueba {

    @GetMapping("/saludar")
    public String saludar() {
        return "Hola desde User Microservice";
    }

    @GetMapping("/despedir")
    public String despedir() {
        return "Adios desde User Microservice";
    }
}
