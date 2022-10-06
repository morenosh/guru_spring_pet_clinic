package org.learning.spring.guruspringpetclinic.bootstrap;

import org.learning.spring.guruspringpetclinic.model.*;
import org.learning.spring.guruspringpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final VisitService visitService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialtyService specialtyService;

    public DataInitializer(
            OwnerService ownerService, VetService vetService,
            VisitService visitService, PetTypeService petTypeService, PetService petService,
            SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.visitService = visitService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialtyService = specialtyService;
    }

    @Override
    public void run(String... args) {
        if (petService.findAll().size() == 0)
            loadData();
    }

    private void loadData() {
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

        var specialty1 = new Speciality();
        specialty1.setDescription("radiology");

        var specialty2 = new Speciality();
        specialty2.setDescription("surgery");

        var specialty3 = new Speciality();
        specialty3.setDescription("dentistry");

        var vet1 = new Vet();
        vet1.setFirstName("Jafar");
        vet1.setLastName("Sadeghi");
        vet1.setSpecialities(new HashSet<>(List.of(specialty1)));

        var vet2 = new Vet();
        vet2.setFirstName("Jasmin");
        vet2.setLastName("Orreily");
        vet2.setSpecialities(new HashSet<>(List.of(specialty2, specialty3)));

        vetService.save(vet1);
        vetService.save(vet2);

        Visit catVisit = new Visit();
        catVisit.setPet(owner2Pet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("sensitive cat");
        visitService.save(catVisit);

        System.out.println("owners size = " + ownerService.findAll().size());
        System.out.println("vets size = " + vetService.findAll().size());
        System.out.println("pet_type size = " + petTypeService.findAll().size());
        System.out.println("pet size = " + petService.findAll().size());
        System.out.println("visit size = " + visitService.findAll().size());

        var specialties = specialtyService.findAll();
        System.out.println("specialty size = " + specialties.size());
        int counter = 1;
        specialties.forEach(speciality ->
                System.out.println("specialty_" + counter + ": id= " + speciality.getId() + " descreption = " + speciality.getDescription()));

        System.out.println("Loaded Vets.....");
    }
}
