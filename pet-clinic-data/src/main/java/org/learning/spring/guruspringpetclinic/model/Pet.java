package org.learning.spring.guruspringpetclinic.model;

import java.time.LocalDate;

public class Pet extends BaseEntity{
    private PetType type;
    private LocalDate birthDate;
    private Owner owner;
}
