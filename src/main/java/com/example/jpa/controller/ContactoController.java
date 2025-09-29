package com.example.jpa.controller;

import com.example.jpa.models.ContactoModel;
import com.example.jpa.reposetory.ContactoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoRepository repo;

    public ContactoController(ContactoRepository repo){
        this.repo= repo;
    }

    @GetMapping
    public List<ContactoModel> listar(){
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<ContactoModel> crear (@RequestBody ContactoModel c){
        if (c.getId() != null ){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactoModel> editar(@PathVariable Long id ,@RequestBody ContactoModel c){
        return repo.findById(id)
                .map(contactoExistente -> {
                    contactoExistente.setNombre(c.getNombre());
                    contactoExistente.setApellido(c.getApellido());
                    contactoExistente.setCategoria(c.getCategoria());
                    contactoExistente.setEmail(c.getEmail());
                    contactoExistente.setTelefono(c.getTelefono());
                    return ResponseEntity.ok(repo.save(contactoExistente));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        return repo.findById(id)
                .map(contacto -> {
                    repo.delete(contacto);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }



    }
