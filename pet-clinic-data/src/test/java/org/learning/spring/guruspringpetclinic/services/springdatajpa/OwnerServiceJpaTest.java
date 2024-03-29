package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Person;
import org.learning.spring.guruspringpetclinic.repositories.OwnerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerServiceJpa ownerServiceJpa;

    Owner sampleOwner1;
    Owner sampleOwner2;
    String commonLastNamePart;

    @BeforeEach
    void setUp() {
        commonLastNamePart = "lastName";
        sampleOwner1 =
                Owner.builder().address("address1").city("city1").telephone("phone1").firstName("firstName1").lastName(commonLastNamePart + 1).build();
        ReflectionTestUtils.setField(sampleOwner1, "id", 1L);
        sampleOwner2 =
                Owner.builder().address("address2").city("city2").telephone("phone2").firstName("firstName2").lastName(commonLastNamePart + 2).build();
        ReflectionTestUtils.setField(sampleOwner2, "id", 2L);
    }

    @Test
    void findAll() {
        Mockito.when(ownerRepository.findAll()).thenReturn(Set.of(sampleOwner1, sampleOwner2));
        var all = ownerServiceJpa.findAll();
        Mockito.verify(ownerRepository, Mockito.times(1)).findAll();
        assertEquals(all.size(), 2);
        var firstNameArray =
                all.stream().sorted(Comparator.comparing(Person::getFirstName)).map(Person::getFirstName).toArray(String[]::new);
        assertArrayEquals(firstNameArray, new String[]{"firstName1", "firstName2"});
    }

    @Test
    void findByIdExist() {
        Mockito.when(ownerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sampleOwner1));

        var o = ownerServiceJpa.findById(1L);
        assertNotNull(o);
        var i1 = o.getId();
        var i2 = sampleOwner1.getId();
        assertEquals(o.getId(), sampleOwner1.getId());
    }

    @Test
    void findByIdNotExist() {
        Mockito.when(ownerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var o = ownerServiceJpa.findById(1L);
        assertNull(o);
    }

    @Test
    void saveNull() {
        var e = assertThrows(RuntimeException.class, () -> ownerServiceJpa.save(null));
        assertEquals(e.getMessage(), "argument can not be null");
    }

    @Test
    void saveNotNull() {
        Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(sampleOwner1);

        var o = ownerServiceJpa.save(sampleOwner1);
        assertEquals(o, sampleOwner1);
    }

    @Test
    void deleteNull() {
        var e = assertThrows(RuntimeException.class, () -> ownerServiceJpa.delete(null));
        assertEquals(e.getMessage(), "argument can not be null");
    }

    @Test
    void deleteIdNull() {
        ReflectionTestUtils.setField(sampleOwner1, "id", null);
        var e = assertThrows(RuntimeException.class, () -> ownerServiceJpa.delete(sampleOwner1));
        assertEquals(e.getMessage(), "id of entity to be deleted can not be null");
    }

    @Test
    void delete() {
        ownerServiceJpa.delete(sampleOwner1);
        Mockito.verify(ownerRepository, Mockito.times(1)).delete(sampleOwner1);
    }

    @Test
    void deleteById() {
        ownerServiceJpa.deleteById(sampleOwner1.getId());
        Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(sampleOwner1.getId());
    }

    @Test
    void findByLastName() {
        //given
        Mockito.when(ownerRepository.findByLastName(sampleOwner1.getLastName())).thenReturn(Optional.of(sampleOwner1));

        //when
        var o = ownerServiceJpa.findByLastName(sampleOwner1.getLastName());

        //then
        assertEquals(o.getLastName(), sampleOwner1.getLastName());
        Mockito.verify(ownerRepository, Mockito.times(1)).findByLastName(sampleOwner1.getLastName());
    }

    @Test
    void findByLastNameNoResult() {
        //given
        var lastName = "alaki";
        Mockito.when(ownerRepository.findByLastName(Mockito.any())).thenReturn(Optional.empty());

        //when
        var owner = ownerServiceJpa.findByLastName(lastName);

        //then
        Assertions.assertNull(owner);
    }

    @Test
    void findByLastNameLikeManyResults() {
        //given
        var ownerList = List.of(sampleOwner1, sampleOwner2);
        Mockito.when(ownerRepository.findAllByLastNameContaining(commonLastNamePart)).thenReturn(ownerList);

        //when
        List<Owner> owners = ownerServiceJpa.findAllByLastNameContaining(commonLastNamePart);

        //then
        Assertions.assertEquals(ownerList.size(), owners.size());
        for (var owner : owners) {
            Assertions.assertTrue(owner.getLastName().contains(commonLastNamePart));
        }
    }

    @Test
    void findByLastNameLikeNoResult() {
        //given
        Mockito.when(ownerRepository.findAllByLastNameContaining(Mockito.any())).thenReturn(List.of());

        //when
        List<Owner> owners = ownerServiceJpa.findAllByLastNameContaining(commonLastNamePart);

        //then
        Assertions.assertTrue(owners.isEmpty());
    }

    @Test
    void findByLastNameLikeOneResult() {
        //given
        var ownerList = List.of(sampleOwner1);
        Mockito.when(ownerRepository.findAllByLastNameContaining(sampleOwner1.getLastName())).thenReturn(ownerList);

        //when
        List<Owner> owners = ownerServiceJpa.findAllByLastNameContaining(sampleOwner1.getLastName());

        //then
        Assertions.assertEquals(1, owners.size());
        Assertions.assertSame(owners.get(0).getLastName(), sampleOwner1.getLastName());
    }
}