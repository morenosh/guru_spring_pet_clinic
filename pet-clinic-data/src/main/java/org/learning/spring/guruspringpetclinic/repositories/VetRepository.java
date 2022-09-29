package org.learning.spring.guruspringpetclinic.repositories;

import org.learning.spring.guruspringpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
