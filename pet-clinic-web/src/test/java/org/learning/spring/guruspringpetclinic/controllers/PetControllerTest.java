package org.learning.spring.guruspringpetclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.spring.guruspringpetclinic.model.Owner;
import org.learning.spring.guruspringpetclinic.model.Pet;
import org.learning.spring.guruspringpetclinic.services.OwnerService;
import org.learning.spring.guruspringpetclinic.services.PetService;
import org.learning.spring.guruspringpetclinic.services.PetTypeService;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    MockMvc mockMvc;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    PetController petController;

    Owner owner;
    Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        owner = Owner.builder().id(ownerId).build();
        Mockito.when(ownerService.findById(ownerId)).thenReturn(owner);
    }

    @Test
    void initCreatePet() throws Exception {
        //given

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId + "/pets/new"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("/pets/createOrUpdatePetForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void processCreatePetSucceed() throws Exception {
        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/owners/" + ownerId + "/pets/new"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + owner.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Mockito.verify(ownerService, Mockito.times(1)).save(owner);
    }

    @Test
    void processCreatePetHasError() throws Exception {
        //given
        Pet pet = Pet.builder().name("petName").build();
        owner.getPets().add(pet);
        var beforePetSize = owner.getPets().size();

        //when
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/owners/" + ownerId + "/pets/new")
                .param("name", pet.getName()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("/pets/createOrUpdatePetForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("pet", hasProperty("name", equalTo(pet.getName()))))
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("pet"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("pet", "name"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("pet", "name", "duplicate"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void initUpdatePet() throws Exception {
        //given
        var petId = 2L;
        var pet = Pet.builder().id(petId).name("petName").birthDate(LocalDate.of(1990, 5, 15)).build();

        //when
        Mockito.when(petService.findById(petId)).thenReturn(pet);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId + "/pets/" + petId +
                "/edit"));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("/pets/createOrUpdatePetForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(petService, Mockito.times(1)).findById(petId);
    }

    @Test
    void processUpdatePetSucceed() throws Exception {
        //given
        Long petId = 2L;
        var petInOwner = Pet.builder().id(petId).name("petName").birthDate(LocalDate.of(1990, 5, 15)).build();
        var petReturnFromPetService = Pet.builder().id(petId).name("petName").birthDate(LocalDate.of(1990, 5, 15)).build();
        owner.getPets().add(petInOwner);

        //when
        Mockito.when(petService.findById(petId)).thenReturn(petReturnFromPetService);
        var resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/owners/" + ownerId + "/pets/" + petId + "/edit")
                .param("name", "newPetName")
                .param("id", petId.toString()));

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + owner.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Mockito.verify(ownerService, Mockito.times(1)).save(owner);
    }

}