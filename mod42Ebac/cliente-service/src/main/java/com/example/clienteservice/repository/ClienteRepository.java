package com.example.clienteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clienteservice.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
 

}
