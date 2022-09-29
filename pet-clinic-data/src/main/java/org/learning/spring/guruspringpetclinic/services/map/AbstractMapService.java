package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.BaseEntity;
import org.learning.spring.guruspringpetclinic.services.CrudService;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> implements CrudService<T, ID> {
    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(ID id) {
        return map.get(id);
    }

    public void deleteById(ID id) {
        map.remove(id);
    }

    public void delete(T object) {
        map.entrySet().removeIf(e -> e.getValue().equals(object));
    }

    public T save(T object) {
        if (object == null) throw new RuntimeException("object cannot be null!");

        if (object.getId() == null)
            object.setId(getNextId());
        map.put((Long)object.getId(), object);
        return object;
    }

    private Long getNextId() {
        if (map.keySet().isEmpty()) return 1L;
        return (Collections.max(map.keySet()) + 1);
    }
}
