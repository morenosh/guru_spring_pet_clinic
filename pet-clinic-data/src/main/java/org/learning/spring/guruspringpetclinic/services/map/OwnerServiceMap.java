package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService{

    private final PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        var all = super.findAll();
        var owner = all.stream().filter(e->e.getLastName().equals(lastName)).findFirst();
        if (owner.isEmpty()) return null;
        return  owner.get();
    }

    @Override
    public List<Owner> findAllByLastNameContaining(String lastName) {
        var all = super.findAll();
        var owners = all.stream().filter(e->e.getLastName().contains(lastName)).collect(Collectors.toList());
        return owners;
    }

    @Override
    public Owner save(Owner owner) {
        if (owner == null) throw new RuntimeException("owner can not be null.");

        owner.getPets().forEach(pet -> {
            if (pet != null){
                if (pet.getId() == null)
                    petService.save(pet);
            }
        });
        return super.save(owner);
    }
}
