package com.exemplo.demo.service;

import com.exemplo.demo.model.SensitiveEntity;
import com.exemplo.demo.repository.SensitiveEntityRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SensitiveEntityService {

    private final SensitiveEntityRepository repository;

    public SensitiveEntityService(SensitiveEntityRepository repository) {
        this.repository = repository;
    }

    public SensitiveEntity save(SensitiveEntity entity) {
        return repository.save(entity);
    }

    public List<SensitiveEntity> findAll() {
        return repository.findAll();
    }

    public SensitiveEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
