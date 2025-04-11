package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.web.OwnerRequest;

import static org.junit.jupiter.api.Assertions.*;

class OwnerEntityMapperTest {

    private final OwnerEntityMapper mapper = new OwnerEntityMapper();

    @Test
    void shouldMapOwnerRequestToOwnerEntity() {
        // Arrange
        OwnerRequest request = new OwnerRequest(
            "Jane",
            "Doe",
            "456 Elm St",
            "Metropolis",
            "9876543210"
        );
        Owner owner = new Owner();

        // Act
        Owner mappedOwner = mapper.map(owner, request);

        // Assert
        assertEquals("Jane", mappedOwner.getFirstName());
        assertEquals("Doe", mappedOwner.getLastName());
        assertEquals("456 Elm St", mappedOwner.getAddress());
        assertEquals("Metropolis", mappedOwner.getCity());
        assertEquals("9876543210", mappedOwner.getTelephone());
    }
}