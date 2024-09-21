package com.exemplo.demo;

import com.exemplo.demo.model.SensitiveEntity;
import com.exemplo.demo.repository.SensitiveEntityRepository;
import com.exemplo.demo.service.SensitiveEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SensitiveEntityServiceTest {

    @Mock
    private SensitiveEntityRepository repository;

    @InjectMocks
    private SensitiveEntityService service;

    private SensitiveEntity entity1;
    private SensitiveEntity entity2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity1 = new SensitiveEntity();
        entity1.setId(1L);
        entity1.setUserDocument("MzYxNDA3ODE4MzM=");
        entity1.setCreditCardToken("YWJjMTIz");
        entity1.setValue(5999L);

        entity2 = new SensitiveEntity();
        entity2.setId(2L);
        entity2.setUserDocument("MzI5NDU0MTA1ODM=");
        entity2.setCreditCardToken("eHl6NDU2");
        entity2.setValue(1000L);
    }

    @Test
    void findAllEntities_shouldReturnAllEntities() {
        // Configurar mock do repositório para retornar a lista de entidades
        when(repository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<SensitiveEntity> entities = service.findAll();
        assertEquals(2, entities.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnEntityWhenExists() {
        // Configurar mock do repositório para retornar uma entidade específica
        when(repository.findById(1L)).thenReturn(Optional.of(entity1));

        SensitiveEntity foundEntity = service.findById(1L);
        assertNotNull(foundEntity);
        assertEquals("MzYxNDA3ODE4MzM=", foundEntity.getUserDocument());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowExceptionWhenEntityDoesNotExist() {
        // Configurar mock do repositório para retornar vazio
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> service.findById(1L));
        assertEquals("Entity not found", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void saveEntity_shouldSaveEntitySuccessfully() {
        // Configurar mock do repositório para salvar a entidade
        when(repository.save(entity1)).thenReturn(entity1);

        SensitiveEntity savedEntity = service.save(entity1);
        assertNotNull(savedEntity);
        assertEquals(entity1.getUserDocument(), savedEntity.getUserDocument());
        verify(repository, times(1)).save(entity1);
    }
}


