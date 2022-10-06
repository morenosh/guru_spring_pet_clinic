package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetTypeServiceMap extends AbstractMapService<PetType, Short> implements PetTypeService {
}
