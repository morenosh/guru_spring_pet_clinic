package org.learning.spring.guruspringpetclinic.services.springdatajpa;

import org.learning.spring.guruspringpetclinic.model.Speciality;
import org.learning.spring.guruspringpetclinic.repositories.SpecialityRepository;
import org.learning.spring.guruspringpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialityServiceJpa implements SpecialtyService {

    private final SpecialityRepository specialityRepository;

    public SpecialityServiceJpa(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        return Streamable.of(specialityRepository.findAll()).toSet();
    }

    @Override
    public Speciality findById(Integer id) {
        return specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        return specialityRepository.save(object);
    }

    @Override
    public void delete(Speciality object) {
        if (object == null) throw new RuntimeException("argument can not be null");
        if (object.getId() == null) throw new RuntimeException("id of entity to be deleted can not be null");

        specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        specialityRepository.deleteById(id);
    }
}
