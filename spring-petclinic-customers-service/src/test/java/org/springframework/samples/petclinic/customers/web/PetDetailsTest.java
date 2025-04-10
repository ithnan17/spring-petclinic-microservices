package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class PetDetailsTest {
    

    
    @Test
    void testPetDetailsFromPet() throws Exception {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");

        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("Cat");

        Pet pet = new Pet();
        pet.setId(101);
        pet.setName("Whiskers");
        pet.setOwner(owner);
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-20");
        pet.setBirthDate(birthDate);
        pet.setType(petType);

        // Act
        PetDetails petDetails = new PetDetails(pet);

        // Assert
        assertEquals(101, petDetails.id());
        assertEquals("Whiskers", petDetails.name());
        assertEquals("John Doe", petDetails.owner());
        assertEquals(birthDate, petDetails.birthDate());
        assertEquals(petType, petDetails.type());
        assertEquals("Cat", petDetails.type().getName());
    }

    @Test
    void testPetDetailsManualConstructor() throws Exception {
        PetType type = new PetType();
        type.setId(2);
        type.setName("Dog");

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01");

        PetDetails petDetails = new PetDetails(99, "Buddy", "Alice Smith", date, type);

        assertEquals(99, petDetails.id());
        assertEquals("Buddy", petDetails.name());
        assertEquals("Alice Smith", petDetails.owner());
        assertEquals(date, petDetails.birthDate());
        assertEquals("Dog", petDetails.type().getName());
    }
}
