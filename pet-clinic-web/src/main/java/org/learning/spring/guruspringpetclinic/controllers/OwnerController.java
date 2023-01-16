package org.learning.spring.guruspringpetclinic.controllers;

import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @InitBinder
    public void setAllowedField(WebDataBinder wenDataBinder){
        wenDataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/find"})
    public ModelAndView findOwners(Owner owner) {
        var modelAndView = new ModelAndView("owners/findOwners");
        modelAndView.addObject(owner);
        return modelAndView;
    }

    @GetMapping("/{ownerId}")
    public ModelAndView displayOwner(@PathVariable Long ownerId) {
        var modelAndView = new ModelAndView("owners/ownerDetails");
        var owner = ownerService.findById(ownerId);
        modelAndView.addObject(owner);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView processFindForm(Owner owner, BindingResult result) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        var ownersFound = ownerService.findAllByLastNameContaining(owner.getLastName());

        if (ownersFound.isEmpty()) {
            result.rejectValue("lastName", "not found", "not found");
            return new ModelAndView("owners/findOwners");
        } else if (ownersFound.size() == 1) {
            // 1 owner found
            owner = ownersFound.get(0);
            return new ModelAndView("redirect:/owners/" + owner.getId());
        } else {
            var modelAndView = new ModelAndView("owners/ownersList");
            modelAndView.addObject("listOwners", ownersFound);
            return modelAndView;
        }
    }
}
