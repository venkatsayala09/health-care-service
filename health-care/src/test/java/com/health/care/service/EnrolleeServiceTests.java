package com.health.care.service;

import com.health.care.dto.DependentDTO;
import com.health.care.dto.EnrolleeDTO;
import com.health.care.entity.ActivationStatus;
import com.health.care.entity.Dependent;
import com.health.care.entity.Enrollee;
import com.health.care.exception.ResourceNotFoundException;
import com.health.care.repository.EnrolleeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class EnrolleeServiceTests {

    @TestConfiguration
    static class EnrolleeServiceImplTestContextConfiguration {
        @Bean
        public EnrolleeService enrolleeService() {
            return new EnrolleeService();
        }
    }

    @Autowired
    private EnrolleeService service;

    @MockBean
    private EnrolleeRepository repository;

    @Before
    public void setUp() {
        Enrollee enrollee = getEnrollee();
        enrollee.setId(1);
        List<Enrollee> allEnrollees = Arrays.asList(enrollee);
        Mockito.when(repository.findById(enrollee.getId())).thenReturn(Optional.of(enrollee));
        Mockito.when(repository.findAll()).thenReturn(allEnrollees);
        Mockito.when(repository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenValidId_thenEnrolleeShouldBeFound() {
        long id = 1l;
        EnrolleeDTO found = service.getEnrolleeInformation(id);
        assertNotNull(found);
        verifyFindByIdIsCalledOnce(id);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInValidId_thenEnrolleeShouldNotBeFound() {
        long id = -99l;
        EnrolleeDTO found = service.getEnrolleeInformation(id);
    }

    @Test
    public void givenEnrollee_whenGetAll_thenReturnRecords() {
        List<EnrolleeDTO> allEnrolleeDTOs = service.getAllEnrollee();
        verifyFindAllEnrolleesIsCalledOnce();
        assertNotNull(allEnrolleeDTOs);
        assertThat(allEnrolleeDTOs).hasSize(1);
    }

    private void verifyFindByIdIsCalledOnce(long id) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).findById(id);
        Mockito.reset(repository);
    }

    private void verifyFindAllEnrolleesIsCalledOnce() {
        Mockito.verify(repository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(repository);
    }

    private Enrollee getEnrollee() {
        Enrollee enrollee = new Enrollee();
        enrollee.setName("Test Enrollee 1");
        enrollee.setPhoneNumber("1234567890");
        Set<Dependent> dependents = new HashSet<>();
        dependents.add(new Dependent("Test Dependent 1", "12-10-1985", enrollee));
        dependents.add(new Dependent("Test Dependent 2", "12-01-1990", enrollee));
        enrollee.setActivationStatus(ActivationStatus.TRUE);
        return enrollee;
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
