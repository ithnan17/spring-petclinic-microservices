package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {
    
    @Test
    void testSetAndGetFirstName() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        assertEquals("John", owner.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        Owner owner = new Owner();
        owner.setLastName("Doe");
        assertEquals("Doe", owner.getLastName());
    }

    @Test
    void testSetAndGetAddress() {
        Owner owner = new Owner();
        owner.setAddress("123 Main St");
        assertEquals("123 Main St", owner.getAddress());
    }

    @Test
    void testSetAndGetCity() {
        Owner owner = new Owner();
        owner.setCity("Springfield");
        assertEquals("Springfield", owner.getCity());
    }

    @Test
    void testSetAndGetTelephone() {
        Owner owner = new Owner();
        owner.setTelephone("123-456-7890");
        assertEquals("123-456-7890", owner.getTelephone());
    }

    @Test
    void testAddPetAndGetPetsSortedByName() {

        Owner owner = new Owner();
        
        Pet petA = new Pet();
        petA.setName("Buddy");
        Pet petB = new Pet();
        petB.setName("Max");

        owner.addPet(petA);
        owner.addPet(petB);

        List<Pet> pets = owner.getPets();

        assertEquals(2, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
        assertEquals("Max", pets.get(1).getName());

        assertSame(owner, petA.getOwner());
        assertSame(owner, petB.getOwner());
    }

    @Test
    void testGetPetsReturnsUnmodifiableList() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("TestPet");
        owner.addPet(pet);

        List<Pet> pets = owner.getPets();

        assertThrows(UnsupportedOperationException.class, () -> pets.add(new Pet()));
    }

    @Test
    void testToStringContainsOwnerInfo() {
        Owner owner = new Owner();
        owner.setFirstName("Jane");
        owner.setLastName("Smith");
        owner.setAddress("456 Main St");
        owner.setCity("New York");
        owner.setTelephone("0987654321");

        String result = owner.toString();

        assertTrue(result.contains("Jane"));
        assertTrue(result.contains("Smith"));
        assertTrue(result.contains("456 Main St"));
        assertTrue(result.contains("New York"));
        assertTrue(result.contains("0987654321"));
    }
}
