package org.springframework.samples.petclinic.customers.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PetRequestTest {
    @Test
    void testPetRequestFields() {
        Date birthDate = new Date();
        PetRequest request = new PetRequest(1, birthDate, "Milo", 5);

        assertEquals(1, request.id());
        assertEquals(birthDate, request.birthDate());
        assertEquals("Milo", request.name());
        assertEquals(5, request.typeId());
    }

    @Test
    void testJsonSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        Date birthDate = new Date(1691664000000L); // 2023-08-10
        PetRequest request = new PetRequest(1, birthDate, "Buddy", 3);

        String json = objectMapper.writeValueAsString(request);

        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"name\":\"Buddy\""));
        assertTrue(json.contains("\"typeId\":3"));
        assertTrue(json.contains("\"birthDate\":\"2023-08-10\""));
    }

    @Test
    void testJsonDeserialization() throws JsonProcessingException {
        String json = """
            {
              "id": 2,
              "birthDate": "2023-08-10",
              "name": "Max",
              "typeId": 4
            }
            """;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        PetRequest request = objectMapper.readValue(json, PetRequest.class);

        assertEquals(2, request.id());
        assertEquals("Max", request.name());
        assertEquals(4, request.typeId());

        // Optional: kiểm tra ngày sinh cụ thể
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("2023-08-10", df.format(request.birthDate()));
    }
}
