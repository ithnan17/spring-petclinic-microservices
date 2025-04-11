package org.springframework.samples.petclinic.customers.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.web.mapper.OwnerEntityMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerResource.class)
@ExtendWith(SpringExtension.class)
public class OwnerResourceTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OwnerRepository ownerRepository;

    @MockBean
    OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldCreateOwner() throws Exception {
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Street", "City", "1234567890");
        Owner owner = new Owner();
        setId(owner, 1);

        Mockito.when(ownerEntityMapper.map(any(), eq(request))).thenReturn(owner);
        Mockito.when(ownerRepository.save(owner)).thenReturn(owner);

        mvc.perform(post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldFindOwnerById() throws Exception {
        Owner owner = new Owner();
        setId(owner, 1);

        Mockito.when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

        mvc.perform(get("/owners/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldFindAllOwners() throws Exception {
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        setId(owner1, 1);
        setId(owner2, 2);

        Mockito.when(ownerRepository.findAll()).thenReturn(List.of(owner1, owner2));

        mvc.perform(get("/owners").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateOwner() throws Exception {
        OwnerRequest request = new OwnerRequest("Jane", "Doe", "456 Avenue", "Town", "9876543210");
        Owner existingOwner = new Owner();
        setId(existingOwner, 1);

        Mockito.when(ownerRepository.findById(1)).thenReturn(Optional.of(existingOwner));
        Mockito.when(ownerRepository.save(any())).thenReturn(existingOwner);

        mvc.perform(put("/owners/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent());
    }

    // Reflection utility
    private void setId(Owner owner, int id) {
        try {
            Field idField = Owner.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(owner, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
