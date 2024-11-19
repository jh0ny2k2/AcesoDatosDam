package com.example.app.controllers;

import com.example.app.entities.Clientes;
import com.example.app.repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesRepository clientesRepository;

    // Obtener todos los clientes
    @GetMapping
    public List<Clientes> getAllClientes() {
        return clientesRepository.findAll();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public Optional<Clientes> getClienteById(@PathVariable Long id) {
        return clientesRepository.findById(id);
    }

    // Crear un nuevo cliente
    @PostMapping
    public Clientes createCliente(@RequestBody Clientes cliente) {
        return clientesRepository.save(cliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public Clientes updateCliente(@PathVariable Long id, @RequestBody Clientes clienteDetails) {
        Clientes cliente = clientesRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteDetails.getNombre());
        cliente.setApellido(clienteDetails.getApellido());
        cliente.setDireccion(clienteDetails.getDireccion());

        return clientesRepository.save(cliente);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clientesRepository.deleteById(id);
    }



}
