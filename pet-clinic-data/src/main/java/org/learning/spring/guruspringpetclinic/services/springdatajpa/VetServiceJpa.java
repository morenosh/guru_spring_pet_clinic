package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.learning.spring.guruspringpetclinic.model.Vet;
import org.learning.spring.guruspringpetclinic.repositories.VetRepository;
import org.learning.spring.guruspringpetclinic.services.VetService;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceJpa implements VetService {

    private final VetRepository vetRepository;

    public VetServiceJpa(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {
        return Streamable.of(vetRepository.findAll()).toSet();
    }

    @Override
    public Vet findById(Long id) {
        return vetRepository.findById(id).orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        return vetRepository.save(object);
    }

    @Override
    public void delete(Vet object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        if (object.getId() == null) throw new RuntimeException("id of entity to be deleted can not be null");

        vetRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}
