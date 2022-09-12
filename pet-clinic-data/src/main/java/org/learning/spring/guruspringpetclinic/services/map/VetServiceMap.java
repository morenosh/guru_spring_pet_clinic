package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Vet;
import org.learning.spring.guruspringpetclinic.services.SpecialtyService;
import org.learning.spring.guruspringpetclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService  {
    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Vet save(Vet vet) {
        if (vet == null) throw new RuntimeException("vet can not be null");
        vet.getSpecialities().forEach(s-> {
            if(s != null)
                if(s.getId() == null)
                    specialtyService.save(s);
        });
        return super.save(vet);
    }
}
