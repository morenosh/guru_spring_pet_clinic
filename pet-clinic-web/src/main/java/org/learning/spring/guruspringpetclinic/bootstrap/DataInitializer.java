package org.learning.spring.guruspringpetclinic.bootstrap;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Vet;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;

    public DataInitializer(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) {
        var owner1 = new Owner();
        owner1.setFirstName("Salimeh");
        owner1.setLastName("Salimi");
        var owner2 = new Owner();
        owner2.setFirstName("Salem");
        owner2.setLastName("Salimi");
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

        System.out.println("Loaded Vets.....");
    }
}
