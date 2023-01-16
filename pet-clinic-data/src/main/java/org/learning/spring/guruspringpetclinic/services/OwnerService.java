package org.learning.spring.guruspringpetclinic.services;

import org.learning.spring.guruspringpetclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameContaining(String lastName);
}
