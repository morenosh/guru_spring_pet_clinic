package org.learning.spring.guruspringpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits;

    @Override
    public String toString(){
        return name.concat(" :").concat(type.getName());
    }
}
