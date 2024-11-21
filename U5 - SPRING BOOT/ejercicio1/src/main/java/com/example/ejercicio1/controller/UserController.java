package com.example.ejercicio1.controller;

import com.example.ejercicio1.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/pruebas")
    public String prueba(Model model) {
        model.addAttribute("titulo", "Titulo de la pagina");
        model.addAttribute("nombre", "Jhony");
        model.addAttribute("apellidos", "Chaves");

        return "prueba";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Hola profe");

        return "contacto";
    }

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<Producto> productos = Arrays.asList(
                new Producto(1, "Ordenador", 500.0),
                new Producto(2, "Ratón", 20.0),
                new Producto(3, "Teclado", 30.0)
        );

        model.addAttribute("productos", productos);
        return "productos";
    }
}
