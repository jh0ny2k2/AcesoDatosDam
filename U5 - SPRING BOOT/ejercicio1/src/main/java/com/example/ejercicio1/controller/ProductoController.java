package com.example.ejercicio1.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @GetMapping("/users")
    public Map<String, Object> getUsers() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Título Hola Mundo");
        body.put("nombre", "Pepe");
        body.put("apellidos", "Gómez");
        return body;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody String entity) {
        try {
            return entity.replace("Gomez", "Gómez");
        } catch (Exception ex) {
            return "Error procesando el dato: " + ex.getMessage();
        }
    }
}
