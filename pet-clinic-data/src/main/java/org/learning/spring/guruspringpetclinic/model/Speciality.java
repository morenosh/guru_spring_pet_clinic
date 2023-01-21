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
@Table(name = "specialities")
public class Speciality extends BaseEntity<Integer> {
    @Column(name = "description")
    private String description;
}
