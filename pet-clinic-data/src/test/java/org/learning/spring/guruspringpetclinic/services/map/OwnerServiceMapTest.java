package org.learning.spring.guruspringpetclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;
    PetService petService;

    Owner sampleOwner;
    @BeforeEach
    void setUp() {
        petService = Mockito.mock(PetService.class);
        ownerServiceMap = new OwnerServiceMap(petService);
        sampleOwner = Owner.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .city("city")
                .telephone("phone").build();
        ownerServiceMap.save(sampleOwner);
    }

    @Test
    void findByLastNameExists() {
        var o = ownerServiceMap.findByLastName(sampleOwner.getLastName());
        assertNotNull(o);
        assertEquals(sampleOwner.getLastName(), o.getLastName());
    }

    @Test
    void findByLastNameNotExists() {
        var o = ownerServiceMap.findByLastName("alaki");
        assertNull(o);
    }

    @Test
    void findByLastNameWhenEmpty() {
        maleOwnersEmpty();
        var o = ownerServiceMap.findByLastName(sampleOwner.getLastName());
        assertNull(o);
    }

    private void maleOwnersEmpty() {
        ownerServiceMap.findAll().forEach(a->ownerServiceMap.delete(a));
    }

    @Test
    void saveNull() {
        var exception = assertThrows(RuntimeException.class, () -> ownerServiceMap.save(null));
        assertEquals(exception.getMessage(), "owner can not be null.");
    }

    @Test
    void saveOwnerHasPet() {
        sampleOwner.setPet(Set.of(Pet.builder().build(), Pet.builder().build()));
        sampleOwner = ownerServiceMap.save(sampleOwner);
        Mockito.verify(petService, Mockito.times(2)).save(Mockito.any(Pet.class));
        assertEquals(sampleOwner.getPet().size(), 2);
    }
}