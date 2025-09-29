package com.example.jpa.controller;

import com.example.jpa.models.CategoriaModel;
import com.example.jpa.reposetory.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository repo;

    public CategoriaController(CategoriaRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<CategoriaModel> listar(){
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<CategoriaModel> crear(@RequestBody CategoriaModel c){
        if (c.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaModel> editar(@PathVariable Long id , @RequestBody CategoriaModel c){
        return repo.findById(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNombre(c.getNombre());
                    return ResponseEntity.ok(repo.save(categoriaExistente));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        return repo.findById(id)
                .map(categoria -> {
                    repo.delete(categoria);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
    }
