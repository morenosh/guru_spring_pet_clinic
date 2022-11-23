package org.learning.spring.guruspringpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Builder
    public Owner(String firstName, String lastName, String address, String city,
                 String telephone, Set<Pet> pet) {
        super(firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pet = pet != null ? pet : new HashSet<>();
    }

    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "telephone")
    private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pet = new HashSet<>();
}
