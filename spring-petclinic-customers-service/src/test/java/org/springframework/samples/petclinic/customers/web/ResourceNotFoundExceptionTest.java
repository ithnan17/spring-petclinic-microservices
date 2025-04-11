package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.web.bind.annotation.ResponseStatus;


class ResourceNotFoundExceptionTest {

    @Test
    void shouldStoreAndReturnMessage() {
        String errorMessage = "Owner with ID 10 not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Not found");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void annotationShouldHaveHttpStatusNotFound() throws NoSuchMethodException {
        ResponseStatus responseStatus = ResourceNotFoundException.class.getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, responseStatus.value());
    }
}