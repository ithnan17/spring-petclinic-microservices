package org.springframework.samples.petclinic.customers.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//
/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
@ActiveProfiles("test")
class PetResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PetRepository petRepository;

    @MockBean
    OwnerRepository ownerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetAPetInJSonFormat() throws Exception {

        Pet pet = setupPet();

        given(petRepository.findById(2)).willReturn(Optional.of(pet));


        mvc.perform(get("/owners/2/pets/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.name").value("Basil"))
            .andExpect(jsonPath("$.type.id").value(6));
    }

        @Test
    void shouldReturnNotFoundForMissingPet() throws Exception {
        given(petRepository.findById(999)).willReturn(Optional.empty());

        mvc.perform(get("/owners/1/pets/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetPetTypes() throws Exception {
        PetType type = new PetType();
        type.setId(1);
        type.setName("Dog");

        given(petRepository.findPetTypes()).willReturn(List.of(type));

        mvc.perform(get("/petTypes").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Dog"));
    }

    @Test
    void shouldCreatePetForOwner() throws Exception {
        Owner owner = new Owner();
        PetType type = new PetType();
        type.setId(1);
        type.setName("Cat");

        Pet newPet = new Pet();
        newPet.setId(5);
        newPet.setName("Milo");
        newPet.setType(type);
        newPet.setBirthDate(new Date());
        newPet.setOwner(owner);

        given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        given(petRepository.findPetTypeById(1)).willReturn(Optional.of(type));
        given(petRepository.save(any(Pet.class))).willReturn(newPet);

        PetRequest request = new PetRequest(0, new Date(), "Milo",  1);
        mvc.perform(post("/owners/1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Milo"))
            .andExpect(jsonPath("$.type.id").value(1));
    }

    @Test
    void shouldUpdatePet() throws Exception {
        Pet pet = setupPet();
        PetType newType = new PetType();
        newType.setId(10);
        newType.setName("Hamster");

        given(petRepository.findById(2)).willReturn(Optional.of(pet));
        given(petRepository.findPetTypeById(10)).willReturn(Optional.of(newType));
        given(petRepository.save(any(Pet.class))).willReturn(pet);

        PetRequest updateRequest = new PetRequest(2, new Date(), "Basil",  10);

        mvc.perform(put("/owners/*/pets/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isNoContent());
    }

    private Pet setupPet() {
        Owner owner = new Owner();
        owner.setFirstName("George");
        owner.setLastName("Bush");

        Pet pet = new Pet();

        pet.setName("Basil");
        pet.setId(2);

        PetType petType = new PetType();
        petType.setId(6);
        pet.setType(petType);

        owner.addPet(pet);
        return pet;
    }
}
