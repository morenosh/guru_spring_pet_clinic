package org.learning.spring.guruspringpetclinic.bootstrap;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.model.Vet;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.learning.spring.guruspringpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    public DataInitializer(
            OwnerService ownerService, VetService vetService,
            PetTypeService petTypeService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public void run(String... args) {
        var petTypeDog = new PetType();
        petTypeDog.setName("Dog");
        petTypeDog = petTypeService.save(petTypeDog);
        var petTypeCat = new PetType();
        petTypeCat.setName("Cat");
        petTypeCat = petTypeService.save(petTypeCat);

        var owner1 = new Owner();
        owner1.setFirstName("Salimeh");
        owner1.setLastName("Salimi");
        owner1.setCity("Chad");
        owner1.setAddress("Akbari blvd, no 2");
        owner1.setTelephone("+18736465884");
        var owner1Pet = new Pet();
        owner1Pet.setName("Kopol");
        owner1Pet.setBirthDate(LocalDate.of(2000, 2, 10));
        owner1Pet.setType(petTypeCat);
        owner1Pet.setOwner(owner1);
        owner1.setPet(new HashSet<>(List.of(owner1Pet)));
        var owner2 = new Owner();
        owner2.setFirstName("Salem");
        owner2.setLastName("Salimi");
        owner2.setCity("Yasooj");
        owner2.setAddress("Teimoori blvd, no 5");
        owner2.setTelephone("+1234567890");
        var owner2Pet = new Pet();
        owner2Pet.setName("Topol");
        owner2Pet.setBirthDate(LocalDate.of(2000, 2, 10));
        owner2Pet.setType(petTypeDog);
        owner2Pet.setOwner(owner2);
        owner2.setPet(new HashSet<>(List.of(owner2Pet)));

        ownerService.save(owner1);
        ownerService.save(owner2);

        var vet1 = new Vet();
        vet1.setFirstName("Jafar");
        vet1.setLastName("Sadeghi");
        var vet2 = new Vet();
        vet2.setFirstName("Jasmin");
        vet2.setLastName("Orreily");
        vetService.save(vet1);
        vetService.save(vet2);

        System.out.println("owners size = " + ownerService.findAll().size());
        System.out.println("vets size = " + vetService.findAll().size());
        System.out.println("pet type size = " + petTypeService.findAll().size());
        System.out.println("pet size = " + petService.findAll().size());
        System.out.println("Loaded Vets.....");
    }
}
