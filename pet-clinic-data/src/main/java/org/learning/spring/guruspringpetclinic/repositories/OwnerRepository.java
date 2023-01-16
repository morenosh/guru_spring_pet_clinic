package org.learning.spring.guruspringpetclinic.repositories;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    List<Owner> findAllByLastNameContaining(String lastNameLike);

    Optional<Owner> findByLastName(String lastName);
}
