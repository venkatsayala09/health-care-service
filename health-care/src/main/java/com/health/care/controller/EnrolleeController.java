package com.health.care.controller;

import com.health.care.dto.EnrolleeDTO;
import com.health.care.exception.ResourceNotFoundException;
import com.health.care.service.EnrolleeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class EnrolleeController {

    @Autowired
    private EnrolleeService service;

    @GetMapping("/enrollee/v1")
    public ResponseEntity<List<EnrolleeDTO>> all() {
        try {
            return new ResponseEntity<>(service.getAllEnrollee(), HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("No Enrollee data available in this system: ");
        }
    }

    @GetMapping("/enrollee/v1/{enrolleeId}")
    public ResponseEntity<EnrolleeDTO> getEnrolleeInformation(@PathVariable long enrolleeId) {
        try {
            return new ResponseEntity<>(service.getEnrolleeInformation(enrolleeId), HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("EnrolleeId : " + enrolleeId + " not found");
        }
    }

    @PostMapping("/enrollee/v1")
    public ResponseEntity<EnrolleeDTO> saveEnrolleeInformation(@Valid @RequestBody EnrolleeDTO enroleeDTO) {
        try {
            return new ResponseEntity<>(service.saveEnrolleeInformation(enroleeDTO), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("Unable to save Enrollee Data");
        }
    }

    @PutMapping("/enrollee/v1/{enrolleeId}")
    public ResponseEntity<EnrolleeDTO> updateEnrolleeInformation(@RequestBody EnrolleeDTO enrolleeDTO, @PathVariable long enrolleeId) {
        try {
            return new ResponseEntity<>(service.updateEnrolleeInformation(enrolleeDTO, enrolleeId), HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("Unable to update Enrollee Data for enroleeId : " + enrolleeDTO.getId());
        }
    }

    @DeleteMapping("/enrollee/v1/{enrolleeId}")
    public ResponseEntity<?> deleteEnrolleeInformation(@PathVariable long enrolleeId) {
        try {
            service.deleteEnrolleeInformation(enrolleeId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to delete Enrollee Data for enroleeId : " + enrolleeId + ".");
        }
    }
}
