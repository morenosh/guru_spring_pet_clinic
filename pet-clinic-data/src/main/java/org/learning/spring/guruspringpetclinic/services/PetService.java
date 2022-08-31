package org.learning.spring.guruspringpetclinic.services;

import org.learning.spring.guruspringpetclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(Long id);
    Pet save(Pet pet);
    Set<Pet> findAll();
}
