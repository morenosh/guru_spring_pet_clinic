package org.learning.spring.guruspringpetclinic.controllers;

import jakarta.validation.Valid;
import org.hibernate.cfg.NotYetImplementedException;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.Visit;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners/{ownerId}/pets/{petId}")
@Controller
public class VisitController {

    private final PetService petService;
    private final OwnerService ownerService;
    private final VisitService visitService;

    public VisitController(PetService petService, OwnerService ownerService, VisitService visitService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.visitService = visitService;
    }

    @ModelAttribute
    public void loadPetAndOwner(@Valid Visit visit, @PathVariable Long petId, @PathVariable Long ownerId, Model model) {
        var pet = petService.findById(petId);
        var owner = ownerService.findById(ownerId);
        if (pet != null) //owner might be null
            model.addAttribute(pet);
        if (owner != null) //owner might be null
            model.addAttribute(owner);
        model.addAttribute(Visit.builder().date(visit.getDate()).description(visit.getDescription()).build());
    }

    @GetMapping("/visits/new")
    ModelAndView initCreateVisit() {
        return new ModelAndView("pets/createOrUpdateVisitForm.html");
    }

    @PostMapping("/visits/new")
    ModelAndView processCreateVisit(Visit visit, Pet pet, Owner owner) {
        if (owner.getId() == null)
            return new ModelAndView("/error.html");
        if (pet.getId() == null)
            return new ModelAndView("/error.html");
        visit.setPet(pet);
        visitService.save(visit);
        return new ModelAndView("redirect:/owners/" + owner.getId());
    }
}
