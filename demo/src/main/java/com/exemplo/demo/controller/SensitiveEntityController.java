package com.exemplo.demo.controller;

import com.exemplo.demo.model.SensitiveEntity;
import com.exemplo.demo.service.SensitiveEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensitive-entities")
public class SensitiveEntityController {

    private final SensitiveEntityService service;

    // Injeção de dependência via construtor
    @Autowired
    public SensitiveEntityController(SensitiveEntityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SensitiveEntity> createEntity(@RequestBody SensitiveEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @GetMapping
    public ResponseEntity<List<SensitiveEntity>> getAllEntities() {
        List<SensitiveEntity> entities = service.findAll();
        if (entities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensitiveEntity> getEntityById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
