package com.example.produtoservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.produtoservice.model.Produto;
import com.example.produtoservice.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    public Produto addProduto(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @GetMapping
    public List<Produto> getAllProdutos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return repository.findById(id)
                         .map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return repository.findById(id)
                         .map(produto -> {
                             produto.setNome(produtoDetails.getNome());
                             produto.setPreco(produtoDetails.getPreco());
                             produto.setQuantidade(produtoDetails.getQuantidade());
                             repository.save(produto);
                             return ResponseEntity.ok(produto);
                         })
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        return repository.findById(id)
                         .map(produto -> {
                             repository.delete(produto);
                             return ResponseEntity.ok().build();
                         })
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
