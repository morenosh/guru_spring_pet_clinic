package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.repositories.PetTypeRepository;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.springframework.data.util.Streamable;

import java.util.Set;

public class PetTypeServiceJpa implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PetTypeServiceJpa(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        return Streamable.of(petTypeRepository.findAll()).toSet();
    }

    @Override
    public PetType findById(Short id) {
        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetType save(PetType object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        return petTypeRepository.save(object);
    }

    @Override
    public void delete(PetType object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        if (object.getId() == null) throw new RuntimeException("id of entity to be deleted can not be null");

        petTypeRepository.delete(object);
    }

    @Override
    public void deleteById(Short id) {
        petTypeRepository.deleteById(id);
    }
}
