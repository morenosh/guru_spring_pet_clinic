package org.learning.spring.guruspringpetclinic.repositories;

import org.learning.spring.guruspringpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
