package org.springframework.samples.petclinic.customers.web;


import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerRequestTest {
    private final Validator validator;

    public OwnerRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidOwnerRequest() {
        OwnerRequest request = new OwnerRequest(
            "John",
            "Doe",
            "123 Main St",
            "Springfield",
            "1234567890"
        );

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testBlankFieldsShouldFailValidation() {
        OwnerRequest request = new OwnerRequest(
            "",    // firstName blank
            "",    // lastName blank
            "",    // address blank
            "",    // city blank
            ""     // telephone blank
        );

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(request);
        assertEquals(6, violations.size(), "Expected 5 validation violations for blank fields");
    }

    @Test
    void testInvalidPhoneNumberShouldFailDigitsConstraint() {
        OwnerRequest request = new OwnerRequest(
            "Jane",
            "Doe",
            "456 Elm St",
            "Hometown",
            "12345abcde" // invalid phone: has letters
        );

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Expected validation violation for telephone");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("telephone")));
    }
}
