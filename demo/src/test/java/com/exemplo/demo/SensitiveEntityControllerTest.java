package com.exemplo.demo;

import com.exemplo.demo.controller.SensitiveEntityController;
import com.exemplo.demo.model.SensitiveEntity;
import com.exemplo.demo.service.SensitiveEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensitiveEntityController.class)
public class SensitiveEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensitiveEntityService service;

    private List<SensitiveEntity> entityList;

    @BeforeEach
    void setUp() {
        // Usando o construtor correto
        SensitiveEntity entity1 = new SensitiveEntity(1L, "MzYxNDA3ODE4MzM=", "YWJjMTIz", 5999L);
        SensitiveEntity entity2 = new SensitiveEntity(2L, "MzI5NDU0MTA1ODM=", "eHl6NDU2", 1000L);
        entityList = Arrays.asList(entity1, entity2);
    }

    @Test
    public void getAllEntities_shouldReturnListOfEntities() throws Exception {
        given(service.findAll()).willReturn(entityList);

        mockMvc.perform(get("/api/sensitive-entities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'userDocument': 'MzYxNDA3ODE4MzM=', 'creditCardToken': 'YWJjMTIz', 'value': 5999},"
                        + "{'userDocument': 'MzI5NDU0MTA1ODM=', 'creditCardToken': 'eHl6NDU2', 'value': 1000}]"));
    }

    @Test
    public void getAllEntities_shouldReturnNoContentWhenListIsEmpty() throws Exception {
        given(service.findAll()).willReturn(Arrays.asList());

        mockMvc.perform(get("/api/sensitive-entities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}

