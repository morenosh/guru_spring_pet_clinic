package org.learning.spring.guruspringpetclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
