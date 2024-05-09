package com.example.clienteservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.clienteservice.model.Cliente;
import com.example.clienteservice.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*") // Permite todas as origens para este controlador
public class ClienteController {
    @Autowired
    private ClienteRepository repository;

    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = repository.findById(id);
        return optionalCliente.map(cliente -> {
            cliente.setNome(clienteDetails.getNome());
            cliente.setEmail(clienteDetails.getEmail());
            cliente.setTelefone(clienteDetails.getTelefone());
            Cliente updatedCliente = repository.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        return repository.findById(id)
            .map(cliente -> {
                repository.delete(cliente);
                return ResponseEntity.ok().build(); // Retorna resposta 200 OK após deletar com sucesso
            })
            .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna resposta 404 Not Found se não encontrar o cliente
    }
    
    @PostMapping("/{id}")
    public Cliente addClienteWithId(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return repository.save(cliente);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ResponseEntity.ok(cliente);
    }
}
