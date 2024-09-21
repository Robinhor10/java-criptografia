package com.exemplo.demo.repository;

import com.exemplo.demo.model.SensitiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveEntityRepository extends JpaRepository<SensitiveEntity, Long> {

}
