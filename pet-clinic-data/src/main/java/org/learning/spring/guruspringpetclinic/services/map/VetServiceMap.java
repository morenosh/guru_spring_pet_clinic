package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Vet;
import org.learning.spring.guruspringpetclinic.services.VetService;

public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService  {
    @Override
    public Vet save(Vet object) {
        return super.save(object.getId(), object);
    }
}
