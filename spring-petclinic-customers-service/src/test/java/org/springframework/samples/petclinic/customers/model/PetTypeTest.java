package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetTypeTest {
    
    @Test
    void testSetAndGetId() {
        PetType petType = new PetType();
        petType.setId(10);
        assertEquals(10, petType.getId(), "PetType ID should be 10");
    }

    @Test
    void testSetAndGetName() {
        PetType petType = new PetType();
        petType.setName("Hamster");
        assertEquals("Hamster", petType.getName(), "PetType name should be 'Hamster'");
    }

    @Test
    void testDefaultValues() {
        PetType petType = new PetType();
        assertNull(petType.getId(), "Default ID should be null");
        assertNull(petType.getName(), "Default name should be null");
    }

    @Test
    void testEqualityByProperties() {
        PetType type1 = new PetType();
        type1.setId(1);
        type1.setName("Cat");

        PetType type2 = new PetType();
        type2.setId(1);
        type2.setName("Cat");

        assertEquals(type1.getId(), type2.getId());
        assertEquals(type1.getName(), type2.getName());
    }

}
