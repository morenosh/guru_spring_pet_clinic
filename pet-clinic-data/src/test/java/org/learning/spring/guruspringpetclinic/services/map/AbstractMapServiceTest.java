package org.learning.spring.guruspringpetclinic.services.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.spring.guruspringpetclinic.model.BaseEntity;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AbstractMapServiceTest {

    AbstractMapService mapService;

    @BeforeEach
    void setUp() {
        mapService = Mockito.mock(AbstractMapService.class, Mockito.CALLS_REAL_METHODS);
        ReflectionTestUtils.setField(mapService, "map", new HashMap<>());
        addEntity(1);
    }

    private void addEntity(int i) {
        var sampleEntity = new BaseEntity(1);
        mapService.save(sampleEntity);
    }

    @Test
    void findAll() {
        var all = mapService.findAll();
        Assertions.assertEquals(all.size(), 1);
    }

    @Test
    void findById() {
        var entity = mapService.findById(1);
        assertNotNull(entity);
    }

    @Test
    void deleteById() {
        mapService.deleteById(1);
        assertNull(mapService.findById(1));
        addEntity(1);
    }

    @Test
    void delete() {
        var e = mapService.findById(1);
        mapService.delete(e);
        assertNull(mapService.findById(1));
    }

    @Test
    void saveNullId() {
        var e = mapService.save(new BaseEntity());
        assertNotNull(e.getId());
        assertNotNull(mapService.findById(e.getId()));
    }

    @Test
    void saveExistingId() {
        var e = mapService.findById(1);
        mapService.save(e);
        assertNotNull(mapService.findById(e.getId()));
    }
}