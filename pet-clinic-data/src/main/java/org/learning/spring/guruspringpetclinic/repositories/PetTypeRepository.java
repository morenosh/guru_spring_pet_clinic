package org.learning.spring.guruspringpetclinic.repositories;

import org.learning.spring.guruspringpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Short> {
}
