package org.learning.spring.guruspringpetclinic.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity<ID extends Number> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    ID id;

    public boolean isNew() {
        return this.id == null;
    }
}
