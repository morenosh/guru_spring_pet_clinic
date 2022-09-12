package org.learning.spring.guruspringpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listPets(Model model){
        return "index.html";
    }

    @RequestMapping({"/oups"})
    public String oups(Model model){
        return "notimplemented.html";
    }

}
