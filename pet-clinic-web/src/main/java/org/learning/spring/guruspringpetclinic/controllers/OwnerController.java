package org.learning.spring.guruspringpetclinic.controllers;

import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index.html";
    }

    @GetMapping({"/find"})
    public String findOwners() {
        return "notimplemented.html";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView displayOwner(@PathVariable Long ownerId) {
        var modelAndView = new ModelAndView("owners/ownerDetails");
        var owner = ownerService.findById(ownerId);
        modelAndView.addObject(owner);
        return modelAndView;
    }
}
