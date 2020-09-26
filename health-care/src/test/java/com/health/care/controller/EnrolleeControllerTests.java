package com.health.care.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.care.dto.DependentDTO;
import com.health.care.dto.EnrolleeDTO;
import com.health.care.entity.ActivationStatus;
import com.health.care.service.EnrolleeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EnrolleeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class EnrolleeControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EnrolleeService service;

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    public void givenEnrollees_whenGetEnrollees_thenReturnJsonArray()
            throws Exception {

        EnrolleeDTO enrollee = getEnrolleeDTO();

        List<EnrolleeDTO> allEnrollees = Arrays.asList(enrollee);

        given(service.getAllEnrollee()).willReturn(allEnrollees);

        mvc.perform(get("/api/enrollee/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(enrollee.getName())));
        verify(service, VerificationModeFactory.times(1)).getAllEnrollee();
        reset(service);
    }

    @Test
    public void givenEnrollee_whenSaveEnrollee_thenReturnJsonObject()
            throws Exception {

        EnrolleeDTO enrollee = getEnrolleeDTO();

        given(service.saveEnrolleeInformation(enrollee)).willReturn(enrollee);

        mvc.perform(post("/api/enrollee/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(enrollee)))
                .andExpect(status().isCreated());
        verify(service, VerificationModeFactory.times(1)).saveEnrolleeInformation(Mockito.any());
        reset(service);
    }

    @Test
    public void givenEnrollee_whenUpdateEnrollee_thenReturnJsonObject()
            throws Exception {

        long id = 1l;
        EnrolleeDTO enrollee = getEnrolleeDTO();
        enrollee.setId(id);

        given(service.updateEnrolleeInformation(enrollee, id)).willReturn(enrollee);

        mvc.perform(put("/api/enrollee/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(enrollee)))
                .andExpect(status().isOk());
        verify(service, VerificationModeFactory.times(1)).updateEnrolleeInformation(enrollee, id);
        reset(service);
    }

    @Test
    public void givenEnrollee_whenDeleteEnrollee()
            throws Exception {
        Mockito.doNothing().when(service).deleteEnrolleeInformation(1);
        mvc.perform(delete("/api/enrollee/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        reset(service);
    }

    private EnrolleeDTO getEnrolleeDTO() {
        EnrolleeDTO enrollee = new EnrolleeDTO();
        enrollee.setName("Test Enrollee 1");
        enrollee.setPhoneNumber("1234567890");
        Set<DependentDTO> dependents = new HashSet<>();
        dependents.add(new DependentDTO("Test Dependent 1", "12-10-1985"));
        dependents.add(new DependentDTO("Test Dependent 2", "12-01-1990"));
        enrollee.setActivationStatus(ActivationStatus.TRUE);
        return enrollee;
    }


}
