package org.learning.spring.guruspringpetclinic.services.map;

import org.learning.spring.guruspringpetclinic.model.Visit;
import org.learning.spring.guruspringpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService{
}
