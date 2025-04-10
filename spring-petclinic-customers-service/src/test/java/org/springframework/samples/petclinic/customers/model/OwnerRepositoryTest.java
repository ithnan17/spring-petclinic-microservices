package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OwnerRepositoryTest {
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testSaveAndFindById() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("1234567890");

        Owner saved = ownerRepository.save(owner);

        Optional<Owner> found = ownerRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
        assertEquals("Doe", found.get().getLastName());
    }

    @Test
    void testFindAll() {
        Owner owner1 = new Owner();
        owner1.setFirstName("Alice");
        owner1.setLastName("Smith");
        owner1.setAddress("1 Road");
        owner1.setCity("CityA");
        owner1.setTelephone("1111111111");

        Owner owner2 = new Owner();
        owner2.setFirstName("Bob");
        owner2.setLastName("Brown");
        owner2.setAddress("2 Avenue");
        owner2.setCity("CityB");
        owner2.setTelephone("2222222222");

        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        assertEquals(2, ownerRepository.findAll().size());
    }
}
