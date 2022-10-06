package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.repositories.PetRepository;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetServiceJpa implements PetService {

    private final PetRepository petRepository;

    public PetServiceJpa(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        return Streamable.of(petRepository.findAll()).toSet();
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public Pet save(Pet object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        return petRepository.save(object);
    }

    @Override
    public void delete(Pet object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        if (object.getId() == null) throw new RuntimeException("id of entity to be deleted can not be null");

        petRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
