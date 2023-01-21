package org.learning.spring.guruspringpetclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity<Short>{
    @Column(name = "name")
    private String name;

    @Override
    public String toString(){
        return this.name;
    }
}
