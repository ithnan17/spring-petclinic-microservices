package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PetRepositoryTest {
    
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("findPetTypes() should return sorted list of PetType")
    void testFindPetTypes() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType cat = new PetType();
        cat.setName("Cat");

        entityManager.persist(dog);
        entityManager.persist(cat);
        entityManager.flush();

        List<PetType> result = petRepository.findPetTypes();

        assertEquals(2, result.size());
        assertEquals("Cat", result.get(0).getName());
        assertEquals("Dog", result.get(1).getName());
    }

    @Test
    @DisplayName("findPetTypeById() should return pet type with given ID")
    void testFindPetTypeById() {
        PetType bird = new PetType();
        bird.setName("Bird");
        bird = entityManager.persistFlushFind(bird);

        Optional<PetType> found = petRepository.findPetTypeById(bird.getId());

        assertTrue(found.isPresent());
        assertEquals("Bird", found.get().getName());
    }

    // Test
    @Test
    @DisplayName("findPetTypeById() should return empty when ID not found")
    void testFindPetTypeById_NotFound() {
        Optional<PetType> found = petRepository.findPetTypeById(999);
        assertTrue(found.isEmpty());
    }
}
