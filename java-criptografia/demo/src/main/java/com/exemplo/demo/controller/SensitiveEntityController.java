package com.exemplo.demo.controller;

import com.exemplo.demo.model.SensitiveEntity;
import com.exemplo.demo.service.SensitiveEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseEntity<List<SensitiveEntity>> getAllEntities() {
        List<SensitiveEntity> entities = service.findAll();
        if (entities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(entities);
    }
}
