package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Speciality;
import org.learning.spring.guruspringpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class SpecialtyServiceMap extends AbstractMapService<Speciality, Integer> implements SpecialtyService {
}
