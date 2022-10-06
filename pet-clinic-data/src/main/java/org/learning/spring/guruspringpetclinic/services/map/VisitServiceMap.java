package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Visit;
import org.learning.spring.guruspringpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService{
}
