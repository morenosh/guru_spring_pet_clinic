package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.repositories.OwnerRepository;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class OwnerServiceJpa implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceJpa(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Set<Owner> findAll() {
        return Streamable.of(ownerRepository.findAll()).toSet();
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        return ownerRepository.save(object);
    }

    @Override
    public void delete(Owner object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        if (object.getId() == null) throw new RuntimeException("id of entity to be deleted can not be null");

        ownerRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName).orElse(null);
    }
}
