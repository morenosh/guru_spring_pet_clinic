package org.learning.spring.guruspringpetclinic.formatter;

import org.learning.spring.guruspringpetclinic.model.PetType;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Locale;

@Controller
public class PetTypeFormatter implements Formatter<PetType> {

    final private PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String name, Locale locale) throws ParseException {
        var allTypes = petTypeService.findAll();
        var petType = allTypes.stream().filter(a -> a.getName().equals(name)).findFirst();
        if (petType.isPresent())
            return petType.get();
        throw new ParseException("type not found: " + name, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
