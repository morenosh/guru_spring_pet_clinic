package org.learning.spring.guruspringpetclinic.services;


import org.learning.spring.guruspringpetclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(Long id);
    Vet save(Vet vet);
    Set<Vet> findAll();
}
