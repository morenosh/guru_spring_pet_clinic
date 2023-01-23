package org.learning.spring.guruspringpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.model.Visit;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.VisitService;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    MockMvc mockMvc;

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;
    private Owner owner;
    private Long petId;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        //given
        petId = 1L;
        ownerId = 2L;
        var pet = Pet.builder().id(petId).build();
        owner = Owner.builder().id(ownerId).build();
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        //when
        Mockito.when(petService.findById(petId)).thenReturn(pet);
    }

    @Test
    void initCreateVisit() throws Exception {
        //given
        Mockito.when(ownerService.findById(ownerId)).thenReturn(owner);

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/owners/" + ownerId + "/pets/" + petId + "/visits/new"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm.html"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"));
    }

    @Test
    void processCreateVisitSucceed() throws Exception {
        //given
        ArgumentCaptor<Visit> visitArgumentCaptor = ArgumentCaptor.forClass(Visit.class);
        var sampleDescription = "sample description";
        Mockito.when(ownerService.findById(ownerId)).thenReturn(owner);

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/owners/" + ownerId + "/pets/" + petId + "/visits/new")
                .param("description", sampleDescription));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        Mockito.verify(visitService, Mockito.times(1)).save(visitArgumentCaptor.capture());
        assertEquals(visitArgumentCaptor.getValue().getDescription(), sampleDescription);
    }

    @Test
    void processCreateVisitWrongOwner() throws Exception {
        //given
        ArgumentCaptor<Visit> visitArgumentCaptor = ArgumentCaptor.forClass(Visit.class);
        var sampleDescription = "sample description";
        Mockito.when(ownerService.findById(4L)).thenReturn(null);

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/owners/" + 4 + "/pets/" + petId + "/visits/new"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("/error.html"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}