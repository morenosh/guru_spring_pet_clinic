package org.learning.spring.guruspringpetclinic.services;

import org.learning.spring.guruspringpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
