package org.learning.spring.guruspringpetclinic.controllers;

import jakarta.validation.Valid;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    final private PetTypeService petTypeService;
    final private OwnerService ownerService;
    final private PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetType() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable Long ownerId,
                       @PathVariable(required = false) Long petId) {
        return petId == null ? new Pet() : petService.findById(petId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public ModelAndView initCreatePet() {
        return new ModelAndView("/pets/createOrUpdatePetForm");
    }

    @PostMapping("/pets/new")
    public ModelAndView processCreatePet(Owner owner, @Valid Pet pet, BindingResult result) {
        if (pet.getName() != null && pet.isNew() && owner.getPets().stream().anyMatch(a -> a.getName().equals(pet.getName())))
            result.rejectValue("name", "duplicate", "already exists");

        if (result.hasErrors()) {
            var errorReturn = new ModelAndView("/pets/createOrUpdatePetForm");
            errorReturn.addObject("pet", pet);
            return errorReturn;
        }

        owner.getPets().add(pet);
        pet.setOwner(owner);
        ownerService.save(owner);
        return new ModelAndView("redirect:/owners/" + owner.getId());
    }

    @GetMapping("/pets/{petId}/edit")
    public ModelAndView initUpdatePet(Pet pet, Model model) {
        model.addAttribute(pet); // pet has been added by @ModelAttribute("pet")
        return new ModelAndView("/pets/createOrUpdatePetForm");
    }

    @PostMapping("/pets/{petId}/edit")
    public ModelAndView processUpdatePet(@Valid Pet pet, Owner owner, BindingResult result, Model model) {
        if (result.hasErrors()) {
            var errorReturn = new ModelAndView("/pets/createOrUpdatePetForm");
            errorReturn.addObject("pet", pet);
            return errorReturn;
        }

        owner.getPets().removeIf(p->p.getId().equals(pet.getId()));
        owner.getPets().add(pet);
        ownerService.save(owner);
        return new ModelAndView("redirect:/owners/" + owner.getId());
    }

}
