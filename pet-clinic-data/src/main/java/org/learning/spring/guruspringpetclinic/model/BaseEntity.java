package org.learning.spring.guruspringpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<T extends Number> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
