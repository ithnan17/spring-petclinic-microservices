package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class PetTest {

    @Test
    void testSetAndGetId() {
        Pet pet = new Pet();
        pet.setId(100);
        assertEquals(100, pet.getId());
    }

    @Test
    void testSetAndGetName() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        assertEquals("Buddy", pet.getName());
    }

    @Test
    void testSetAndGetBirthDate() {
        Pet pet = new Pet();
        Date date = new Date();
        pet.setBirthDate(date);
        assertEquals(date, pet.getBirthDate());
    }

    @Test
    void testSetAndGetType() {
        Pet pet = new Pet();
        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("Dog");
        pet.setType(petType);

        assertEquals("Dog", pet.getType().getName());
        assertEquals(1, pet.getType().getId());
    }

    @Test
    void testSetAndGetOwner() {
        Pet pet = new Pet();
        Owner owner = new Owner();
        owner.setFirstName("Alice");
        owner.setLastName("Smith");
        pet.setOwner(owner);

        assertEquals("Alice", pet.getOwner().getFirstName());
        assertEquals("Smith", pet.getOwner().getLastName());
    }

    @Test
    void testEqualsAndHashCode() {
        Pet pet1 = new Pet();
        Pet pet2 = new Pet();

        Date date = new Date();
        PetType type = new PetType();
        type.setId(1);
        type.setName("Cat");

        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");

        pet1.setId(1);
        pet1.setName("Whiskers");
        pet1.setBirthDate(date);
        pet1.setType(type);
        pet1.setOwner(owner);

        pet2.setId(1);
        pet2.setName("Whiskers");
        pet2.setBirthDate(date);
        pet2.setType(type);
        pet2.setOwner(owner);

        assertEquals(pet1, pet2);
        assertEquals(pet1.hashCode(), pet2.hashCode());
    }

    @Test
    void testToStringDoesNotThrowException() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Charlie");

        PetType type = new PetType();
        type.setName("Dog");
        pet.setType(type);

        Owner owner = new Owner();
        owner.setFirstName("Bob");
        owner.setLastName("Marley");
        pet.setOwner(owner);

        String result = pet.toString();
        assertTrue(result.contains("Charlie"));
        assertTrue(result.contains("Dog"));
        assertTrue(result.contains("Bob"));
    }
    
}
