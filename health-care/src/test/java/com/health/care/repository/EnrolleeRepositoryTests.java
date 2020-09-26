package com.health.care.repository;

import com.health.care.entity.ActivationStatus;
import com.health.care.entity.Dependent;
import com.health.care.entity.Enrollee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EnrolleeRepositoryTests {

    @Autowired
    private EnrolleeRepository repository;

    @Test
    public void testSaveEnrollee() {
        Enrollee enrollee = getEnrollee();
        repository.save(enrollee);
        Optional<Enrollee> enrolleeOptional = repository.findById(1l);
        Enrollee enrollee2 = enrolleeOptional.isPresent() ? enrolleeOptional.get() : null;
        assertNotNull(enrollee2);
        assertEquals(enrollee, enrollee2);
    }

    @Test
    public void testGetEnrollee() {
        Enrollee enrollee = getEnrollee();
        repository.save(enrollee);
        Optional<Enrollee> enrolleeOptional = repository.findById(enrollee.getId());
        Enrollee enrollee2 = enrolleeOptional.isPresent() ? enrolleeOptional.get() : null;
        assertNotNull(enrollee2);
        assertEquals(enrollee, enrollee2);
    }

    @Test
    public void testDeleteEnrollee() {
        Enrollee enrollee = getEnrollee();
        repository.save(enrollee);
        repository.delete(enrollee);
    }

    @Test
    public void findAllEnrollee() {
        Enrollee enrollee = getEnrollee();
        repository.save(enrollee);
        assertNotNull(repository.findAll());
    }

    @Test
    public void deletByEnrolleeIdTest() {
        Enrollee enrollee = getEnrollee();
        repository.save(enrollee);
        repository.deleteById(enrollee.getId());
    }

    private Enrollee getEnrollee(){
        Enrollee enrollee = new Enrollee();
        enrollee.setName("Test Enrollee 1");
        enrollee.setPhoneNumber("1234567890");
        Set<Dependent> dependents = new HashSet<>();
        dependents.add(new Dependent("Test Dependent 1", "12-10-1985", enrollee));
        dependents.add(new Dependent("Test Dependent 2", "12-01-1990", enrollee));
        enrollee.setActivationStatus(ActivationStatus.TRUE);
        return enrollee;
    }
}
