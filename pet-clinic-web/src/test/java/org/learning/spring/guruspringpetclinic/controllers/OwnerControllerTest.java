package org.learning.spring.guruspringpetclinic.controllers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().build());
        owners.add(Owner.builder().build());
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void findOwners() throws Exception {
        //given

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormManyResults() throws Exception {
        //given
        var commonString = "common";
        var lastName1 = commonString + 1;
        var lastName2 = commonString + 2;
        var owner1 = Owner.builder().lastName(lastName1).build();
        var owner2 = Owner.builder().lastName(lastName2).build();
        var listOfOwner = List.of(owner1, owner2);

        //when
        Mockito.when(ownerService.findAllByLastNameContaining(commonString)).thenReturn(listOfOwner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", commonString));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("listOwners"));
    }

    @Test
    void processFindFormOneResult() throws Exception {
        //given
        var lastName = "testLastName";
        var owner = Owner.builder().lastName(lastName).id(1L).build();
        var listOfOwner = List.of(owner);

        //when
        Mockito.when(ownerService.findAllByLastNameContaining(lastName)).thenReturn(listOfOwner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", lastName));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + owner.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName",
                        Matchers.is(lastName))));
    }

    @Test
    void processFindFormNoResult() throws Exception {
        //given
        var query = Owner.builder().lastName("searchedQuery").build();
        List<Owner> listOfOwner = List.of();

        //when
        Mockito.when(ownerService.findAllByLastNameContaining(Mockito.any())).thenReturn(listOfOwner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName",
                query.getLastName()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName",
                        Matchers.is(query.getLastName()))));
    }

    @Test
    void showOwnerDetails() throws Exception {
        //given
        var ownerId = 1364L;
        var dummyOwner = Owner.builder().id(ownerId).build();

        //when
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(dummyOwner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner",
                        Matchers.hasProperty("id", Matchers.is(ownerId))));
    }

    @Test
    void initCreateOwner() throws Exception {
        //given
        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/new"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    void processCreateOwner() throws Exception {
        //given
        Long ownerId = 1L;
        var owner = Owner.builder().id(ownerId).build();

        //when
        Mockito.when(ownerService.save(Mockito.any())).thenReturn(owner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/owners/new")
                .param("id", ownerId.toString()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        Mockito.verify(ownerService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void initUpdateOwner() throws Exception {
        //given
        var ownerId = 1L;
        var owner = Owner.builder().id(ownerId).build();

        //when
        Mockito.when(ownerService.findById(ownerId)).thenReturn(owner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId + "/edit"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("owner",
                        Matchers.hasProperty("id", Matchers.is(ownerId))));

        Mockito.verify(ownerService, Mockito.times(1)).findById(ownerId);
    }

    @Test
    void processUpdateOwner() throws Exception {
        //given
        var ownerId = 1L;
        var owner = Owner.builder().id(ownerId).build();

        //when
        Mockito.when(ownerService.save(Mockito.any())).thenReturn(owner);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/owners/" + ownerId + "/edit", owner));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId))
                .andExpect(MockMvcResultMatchers.model().attribute("owner",
                        Matchers.hasProperty("id", Matchers.is(ownerId))));
    }
}